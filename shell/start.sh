#!/bin/bash

ENV=$1
APP_NAME=stickfit
APP_HOME=/app/${APP_NAME}

mkdir -p ${APP_HOME}/log

java -jar -Dspring.profiles.active=${ENV} -DAPP_NAME=${APP_NAME} ${APP_HOME}/${APP_NAME}.jar >> ${APP_HOME}/log/console.log 2>&1 &