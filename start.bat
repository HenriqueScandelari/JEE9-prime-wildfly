@echo off
call mvn clean install
call copy .\target\prime-wild.war C:\Work\Java\wildfly-27.0.1.Final\standalone\deployments\
