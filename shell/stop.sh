#!/bin/bash

ENV=$1
APP_NAME=stickfit

PID=`ps -ef | grep java | grep "APP_NAME=${APP_NAME} " | grep 'spring.profiles.active=${ENV}' | awk '{ print $2 }'`

if [ "${PID}" == "" ];then
	echo "${APP_NAME} is not runing! skip stop. "
else
	kill ${PID}
	echo "${APP_NAME} is stopped on pid=${PID}!"
fi
