# Introduction
This tutorial will walk you through the process of creating springboot microservice integration with IBM MQ and register them to springboot admin for monitoring the application. The deployment will be done using docker compose.
The tutorial assumes that users have basic knowledge of springboot, springboot admin and docker.

The springboot microservice has the below features

    SpringBoot sample application
    Custom field level Validation using javax validation and annotation
    API documentation using swagger
    IBM MQ integration to convert JSON request payload to XML and post to IBM MQ.
    Log configuration using logback.xml
    Build and deploy the application using docker and docker compose

1. [Build docker images](#build-docker-images)
2. [Deployment using docker compose](#deployment-using-docker-compose)
3. [Docker compose options](#docker-compose-options)

## Build docker images

1. Clone the repo from github
2. cd springbootadmin and follow the [README.md](https://github.com/shivchikkappa/SpringbootIBMMQ/blob/master/springbootadmin/README.md) to build the docker image 
3. cd service and follow the instuctions from [README.md](https://github.com/shivchikkappa/SpringbootIBMMQ/blob/master/service/README.md) to build the docker image.

## Deployment using docker compose

1. Start the containers using docker compose. The -d flag will start the containers in the background.
```bash
docker compose up -d 
```
2. Access the springboo admin console at http://localhost:9060 to confirm springboot microsevice is registed and status is "up". Use the credentials configured in springboot admin [application.properties](https://github.com/shivchikkappa/SpringbootIBMMQ/blob/master/springbootadmin/src/main/resources/application.properties).

3. Access the Swagger APi docmentation at http://localhost:8080/swagger-ui.html#/

4. Start the containers using docker compose
```bash
docker compose down 
``` 

## Docker compose options

docker-compose config --> Check for errors in docker compose file

version  --> Docker compose version

networks --> Docker network to be created for the deployment

services --> List of services to be started. For this tutorial we are starting springboot service, sprinboot admin and IBM MQ. Detailed explanation of a service configurations

```bash
services:
    config-service: <Unique name for list of services >
        container_name: <Unique name for the container>
        image: <Docker image to be used to start the containers>
        ports: <Ports to be exposed. Read as external_port:internal_port>
            - 8080:9080
            - 8085:9085
        depends_on: <Depedent services to be started>
           - config-ibmmq
        environment: <Springboot application propertis override>
          SPRING_APPLICATION_NAME: Service-API-IBMMQ
          IBM_MQ_CONNNAME: ibmcommq(1414)
        networks: <Docker network to be associated with the container>
            - spring-admin-network
```
