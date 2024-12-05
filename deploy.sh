#!/bin/bash

APP_DIR="${APP_DIR:-/home/ec2-user/todo}"
JAR_PATH="${APP_DIR}/todo.jar"

PID=$(lsof -t -i:8080)
if [ ! -z "$PID" ]; then
  echo "Killing the old application with PID: $PID"
  kill -9 $PID
fi

echo "Starting new application..."
nohup java -jar "$JAR_PATH" > /dev/null 2>&1 &
echo "Application started!"
