#!/bin/bash

#

APP_NAME=stickfit
JAVA_HOME=/usr/java/jdk1.8.0_144
M2_HOME=/usr/local/apache-maven-3.5.0

APP_HOME=/app/${APP_NAME}

mkdir -p ${APP_HOME}

PATH=$PATH:${JAVA_HOME}/bin:${M2_HOME}/bin

####################################################
# stop app
####################################################
sh ${APP_HOME}/bin/stop.sh

####################################################
# source compile
####################################################
cd ${APP_NAME}
mvn clean package

####################################################
# copy jar
####################################################
rm -rf ./${APP_NAME}
mkdir -p ./${APP_NAME}/bin

cp -R ./shell/* ./${APP_NAME}/bin/
cp ./server/target/server*.jar ./${APP_NAME}/${APP_NAME}.jar

rm -rf ${APP_HOME}
cp -R ./${APP_NAME} ${APP_HOME}

####################################################
# start app
####################################################

sh ${APP_HOME}/bin/start.sh
