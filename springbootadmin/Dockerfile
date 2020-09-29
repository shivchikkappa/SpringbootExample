# SpringbootAdmin

FROM openjdk:11-jdk-slim

LABEL maintainer="gowda.shivakumar@gmail.com"

VOLUME /tmp

EXPOSE 9060 9065

ARG JAR_FILE=/target/springbootadmin-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} springbootadmin.jar

ENTRYPOINT ["java","-jar", "/springbootadmin.jar"]
