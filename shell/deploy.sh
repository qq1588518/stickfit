#!/bin/bash

#

ENV=$1

APP_NAME=stickfit
JAVA_HOME=/usr/java/jdk1.8.0_144
M2_HOME=/usr/local/apache-maven-3.5.0

APP_HOME=/app/${APP_NAME}

if [ "${ENV}" -eq "dev" ]; then
  APP_HOME=/appdev/${APP_NAME}
fi
  
mkdir -p ${APP_HOME}

PATH=$PATH:${JAVA_HOME}/bin:${M2_HOME}/bin

####################################################
# stop app
####################################################
sh ${APP_HOME}/bin/stop.sh ${ENV}

####################################################
# source compile
####################################################
cd ./server
mvn clean package

####################################################
# copy jar
####################################################
rm -rf ./${APP_NAME}
mkdir -p ./${APP_NAME}/bin

cp -R ../shell/* ./${APP_NAME}/bin/
cp ./target/${APP_NAME}*.jar ./${APP_NAME}/${APP_NAME}.jar

rm -rf ${APP_HOME}
cp -R ./${APP_NAME} ${APP_HOME}

####################################################
# start app
####################################################

sh ${APP_HOME}/bin/start.sh ${ENV}
