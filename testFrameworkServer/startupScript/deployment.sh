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

  
# 
# Code
# 

function func_rm
{
  for i in `cat ${initFile} | grep "Client*[0-9] | awk -F"=" '{ print $2 }'`
  do
    ssh -q student@${i} "rm ${remotePath}/${clientJar}"
  done
}

function func_copy
{
  cd ${clientJarPath}
  for i in `cat ${initFile} | grep "Client*[0-9] | awk -F"=" '{ print $2 }'`
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
  java -jar ${serverJar}
  cd ${startFolder}
}

function func_startClient
{
  for i in `cat ${initFile} | grep "Client*[0-9] | awk -F"=" '{ print $2 }'`
  do
    ssh student@${i} "java -jar ${remotePath}/${clientJar}" &
  echo "STARTUPSCRIPT: Client with ${i} started"
  done
  echo "STARTUPSCRIPT: All clients started"
}

func_rm
sleep 2
func_copy
func_startClient
sleep 2
func_startServer
