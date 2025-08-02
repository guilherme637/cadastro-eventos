#!/bin/sh

JAR_PATH="/home/petrobras/target/petrobras-0.0.1-SNAPSHOT.jar"

if [ ! -f "$JAR_PATH" ]; then
    echo "Compilando"
    mvn clean package -DskipTests
fi

if [ -f "$JAR_PATH" ]; then
    cp "$JAR_PATH" /usr/local/lib/petrobras-0.0.1-SNAPSHOT.jar
else
    echo "JAR não encontrado em $JAR_PATH"
    exit 1
fi

if [ -d "/target-export" ]; then
    rm -rf /target-export/*
    cp -r /home/petrobras/target/* /target-export/
fi

if [ "$DEBUG" = "true" ]; then
    echo "Rodando com debug ativado"
    java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$PORT_DEBUG \
         -jar /usr/local/lib/petrobras-0.0.1-SNAPSHOT.jar
else
    echo "Iniciando app sem debug"
    java -jar /usr/local/lib/petrobras-0.0.1-SNAPSHOT.jar
fi