#!/usr/bin/env bash

set -e

docker run -d --name ElevatorApplication -p 8081:8081 234539630134.dkr.ecr.us-east-1.amazonaws.com/elevatorapplication:latest