#!/bin/bash

JAR_PATH="${APP_DIR}/todo.jar"
LOG_DIR="${APP_DIR}/logs"
LOG_PATH="${LOG_DIR}/todo.log"

mkdir -p "$LOG_DIR"
touch "$LOG_PATH"

PID=$(lsof -t -i:8080)
if [ ! -z "$PID" ]; then
  echo "Killing the old application with PID: $PID"
  kill -9 $PID
fi

echo "Starting new application..."
nohup java -jar "$JAR_PATH" > "$LOG_PATH" 2>&1 &
echo "Application started!"
