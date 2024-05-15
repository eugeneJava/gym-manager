#!/bin/bash

cd ~/IdeaProjects/gym-manager/backend/target
cp backend-1.0.jar /home/evgeniy/release/

FILE="/home/evgeniy/release/backend-1.0.jar"

# Check if the file exists
if [ -f "$FILE" ]; then
    echo "The file $FILE copied successfully."
else
    echo "The file $FILE does not exist."
fi