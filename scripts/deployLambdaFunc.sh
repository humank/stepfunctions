#!/usr/bin/env bash

# Request Spot handler

aws lambda create-function \
--function-name RequestSpotHandler \
--runtime java8 \
--role arn:aws:iam::584518143473:role/LambdaRole \
--handler solid.humank.statehandler.RequestSpotHandler::handleRequest \
--vpc-config SubnetIds=subnet-f7a263ac,subnet-74f8703d,SecurityGroupIds=sg-facb3583 \
--tags Name=QA-Env-Lambda-RequestSpot \
--zip-file fileb://~/git/stepfunctions/build/libs/stepfunctions-1.0-SNAPSHOT.jar \
--memory-szie 1024
--timeout 300

# Request Spot Fail

aws lambda create-function \
--function-name RequestSpotFailHandler \
--runtime java8 \
--role arn:aws:iam::584518143473:role/LambdaRole \
--handler solid.humank.statehandler.RequestSpotFailHandler::handleRequest \
--vpc-config SubnetIds=subnet-f7a263ac,subnet-74f8703d,SecurityGroupIds=sg-facb3583 \
--tags Name=QA-Env-Lambda-RequestSpot \
--zip-file fileb://~/git/stepfunctions/build/libs/stepfunctions-1.0-SNAPSHOT.jar
--memory-szie 1024 \
--timeout 300

# Request Spot Success

aws lambda create-function \
--function-name RequestSpotSuccessHandler \
--runtime java8 \
--role arn:aws:iam::584518143473:role/LambdaRole \
--handler solid.humank.statehandler.RequestSpotSuccessHandler::handleRequest \
--vpc-config SubnetIds=subnet-f7a263ac,subnet-74f8703d,SecurityGroupIds=sg-facb3583 \
--tags Name=QA-Env-Lambda-RequestSpot \
--zip-file fileb://~/git/stepfunctions/build/libs/stepfunctions-1.0-SNAPSHOT.jar
--memory-szie 1024 \
--timeout 300

# Result Notify handler

aws lambda create-function \
--function-name ResultNotifyHandler \
--runtime java8 \
--role arn:aws:iam::584518143473:role/LambdaRole \
--handler solid.humank.statehandler.ResultNotifyHandler::handleRequest \
--vpc-config SubnetIds=subnet-f7a263ac,subnet-74f8703d,SecurityGroupIds=sg-facb3583 \
--tags Name=QA-Env-Lambda-RequestSpot \
--zip-file fileb://~/git/stepfunctions/build/libs/stepfunctions-1.0-SNAPSHOT.jar
--memory-szie 1024 \
--timeout 300
