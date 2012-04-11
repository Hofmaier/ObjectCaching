#!/bin/bash

#
# This script copies a jar file to a remote host. It starts the server application on the local machine and afterwards the client application
# on the remote hosts.
#
# HSR, timmi, raphi, lukas, 18.03.2012

# 
# Variables
# 

startFolder="`pwd`"
swpw="${startFolder}/myKey"
clientJar="client.jar"
serverJar="server.jar"
remotePath="/home/student/Downloads"
clientJarPath="../../dist/client"
serverJarPath="../../dist/server"
initFile="../initFile.conf"
clientTemp="clients"

  
# 
# Code
# 

function func_create_CLient_List
{
  cat ${initFile} | grep "Client*[0-9]" | awk -F"=" '{ print $2 }'> ${clientTemp} 
}

function func_get_CLient_RMI_Port
{
  clientPort=$( cat ${initFile} | grep "Clientport" | awk -F"=" '{ print $2 }' )
}

function func_rm
{
  for i in `cat ${clientTemp}`
  do
    ssh -q student@${i} "rm ${remotePath}/${clientJar}"
  done
}

function func_copy
{
  cd ${clientJarPath}
  for i in `cat "${startFolder}/${clientTemp}"`
  do
    scp ${clientJar} student@${i}:"${remotePath}/${clientJar}"
  done
  echo "STARTUPSCRIPT: Clients deployed"
  cd ${startFolder}
}

function func_startServer
{
  cd ${serverJarPath}
  echo "STARTUPSCRIPT: Server started"
  java -jar ${serverJar} $1
  cd ${startFolder}
  rm ${clientTemp}
}

function func_startClient
{
  for i in `cat "${startFolder}/${clientTemp}"`
  do
    ssh student@${i} "java -jar ${remotePath}/${clientJar} ${clientPort}" &
  echo "STARTUPSCRIPT: Client with ${i} started"
  done
  echo "STARTUPSCRIPT: All clients started"
}
ls ${serverJarPath} | grep $1 > /dev/null 2>&1
if [[ $? -eq 0 || "$1" = "" ]]
then
	func_create_CLient_List
	sleep 1
	func_get_CLient_RMI_Port
	sleep 1
	func_rm
	sleep 2
	func_copy
	func_startClient
	sleep 4
	func_startServer $1
else
	echo "File $1 does not exist! Aborting..."
fi
