#!/usr/bin/env bash

set -e

#Login to ECR Repository
LOGIN_STRING=`aws ecr get-login`
${LOGIN_STRING}

docker pull 234539630134.dkr.ecr.us-east-1.amazonaws.com/elevatorapplication:latest