FROM openjdk:11-jdk-slim

LABEL maintainer="gowda.shivakumar@gmail.com"

VOLUME /tmp

ARG JAR_FILE=/target/SpringbootIBMMQ-0.0.1-SNAPSHOT.jar
ARG LOGBACK_FILE=/src/main/resources/logback-spring.xml

ADD ${JAR_FILE} springbootIBMMQ-service.jar
ADD ${LOGBACK_FILE} logback-spring.xml

ENTRYPOINT ["java","-jar", "/springbootIBMMQ-service.jar"]
