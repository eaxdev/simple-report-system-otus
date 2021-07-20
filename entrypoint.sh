#!/usr/bin/env sh

java "-agentlib:jdwp=transport=dt_socket,address=*:8000,server=y,suspend=n" -XX:+UseContainerSupport -Xmx512m -Xss512k -XX:MetaspaceSize=100m --enable-preview -jar /app/application-0.0.1.jar