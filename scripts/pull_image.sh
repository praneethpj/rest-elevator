#!/usr/bin/env bash

set -e

docker run -d --name ElevatorApplication -p 8081:8081 elevatorapplication:latest