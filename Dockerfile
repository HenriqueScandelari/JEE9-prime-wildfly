FROM quay.io/wildfly/wildfly:27.0.1.Final-jdk17
COPY database/postgresql-42.7.8.jar /opt/jboss/wildfly/modules/system/layers/base/org/postgreSQL/main/
COPY database/module.xml /opt/jboss/wildfly/modules/system/layers/base/org/postgreSQL/main/
COPY database/standalone.xml /opt/jboss/wildfly/standalone/configuration
ADD target/prime-wild.war /opt/jboss/wildfly/standalone/deployments/