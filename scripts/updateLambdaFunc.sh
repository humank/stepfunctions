#!/usr/bin/env bash

# Request Spot handler

aws lambda update-function-code \
--function-name RequestSpotHandler \
--zip-file fileb://~/git/stepfunctions/build/libs/stepfunctions-1.0-SNAPSHOT.jar


# Request Spot Fail

aws lambda update-function-code \
--function-name RequestSpotFailHandler \
--zip-file fileb://~/git/stepfunctions/build/libs/stepfunctions-1.0-SNAPSHOT.jar

# Request Spot Success

aws lambda update-function-code \
--function-name RequestSpotSuccessHandler \
--zip-file fileb://~/git/stepfunctions/build/libs/stepfunctions-1.0-SNAPSHOT.jar

# Result Notify handler

aws lambda update-function-code \
--function-name ResultNotifyHandler \
--zip-file fileb://~/git/stepfunctions/build/libs/stepfunctions-1.0-SNAPSHOT.jar
