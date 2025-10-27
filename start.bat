@echo off
call docker compose down
call mvn clean install
call docker-compose build
call docker compose up -d
call mvn clean package
call docker build -t br.scandelari/JeePrimeTest .
call docker rm -f JeePrimeTest
call docker run -d -p 9080:9080 -p 9443:9443 --name JeePrimeTest br.scandelari/JeePrimeTest