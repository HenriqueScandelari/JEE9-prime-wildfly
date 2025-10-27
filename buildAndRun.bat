echo Start Deploy and Run
@echo off
call docker compose down
call mvn clean install
call docker-compose build
call docker compose up -d
