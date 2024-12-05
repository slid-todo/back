#!/bin/bash

APP_DIR="${APP_DIR:-/home/ec2-user/todo}"
JAR_PATH="${APP_DIR}/todo-0.0.1-SNAPSHOT.jar"
LOG_DIR="${APP_DIR}/logs"
DEPLOY_DATE=$(date +'%Y-%m-%d')

mkdir -p "$LOG_DIR"

PID=$(lsof -t -i:8080)
if [ ! -z "$PID" ]; then
  echo "Killing the old application with PID: $PID"
  kill -9 $PID
fi

LOG_FILE="${LOG_DIR}/todo-${DEPLOY_DATE}.log"

echo "Starting new application..."
nohup java -jar "$JAR_PATH" > "$LOG_FILE" 2>&1 &
echo "Application started!"
