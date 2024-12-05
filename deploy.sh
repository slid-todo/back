#!/bin/bash

APP_DIR="${APP_DIR:-/home/ec2-user/todo}"
JAR_PATH="${APP_DIR}/todo-0.0.1-SNAPSHOT.jar"

PID=$(lsof -t -i:8080)
if [ ! -z "$PID" ]; then
  echo "Killing the old application with PID: $PID"
  kill -9 $PID
fi

echo "Starting new application..."
nohup java -jar "$JAR_PATH" > todo.log 2>&1 &
echo "Application started!"