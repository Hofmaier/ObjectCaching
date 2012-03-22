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
clientJarName="client.jar"
serverJarName="server.jar"
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
  echo "clients deployed"
}

function func_startServer
{
  java -jar ${serverJarName}
  echo "Server started"
}

function func_startClient
{
  for i in `cat ${clients}`
  do
    ssh -q student@${i} "java -jar ${remotePath}/${clientJarName} &"
  echo "client with ${i} started"
  done
  echo "All clients started"
}

func_copy
func_startServer
func_startClient
