#!/bin/sh
docker compose down
mvn clean install
#docker build -t prime-app-wildfly .
docker-compose build
docker compose up -d

