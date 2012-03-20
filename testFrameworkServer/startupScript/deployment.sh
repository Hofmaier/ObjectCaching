#!/bin/bash

#
# This script copies a jar file to a remote host. It starts the server application on the local machine and afterwards the client application
# on the remote hosts.
#
# HSR, timmi, raphi, lukas, 18.03.2012

# 
# Variables
# 

clients="/home/student/bin/deployment/clients"
swpw="/home/student/bin/deployment/myKey"
clientJarName="bla.jar"
serverJarName=""
remotePath="/home/student/Downloads"

  
# 
# Code
# 

function func_copy
{
  for i in `cat ${clients}`
  do
    scp ${clientJarName} student@${i}:"${remotePath}/${clientJarName}"
  done
}

function func_startServer
{
  java -jar ${serverJarName}
}

function func_startClient
{
  for i in `cat ${clients}`
  do
    ( 
    echo "cd ${remotePath}"
    java -jar ${clientJarName} > lala
    ) | /usr/bin/sshpass -f ${swpw} ssh -T -o StrictHostKeyChecking=no "student@${i}"
  done
}

func_copy
#func_startServer
func_startClient
