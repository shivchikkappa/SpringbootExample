# Springboot IBM MQ Example
This tutorial will walk you through the process of creating springboot microservices with below features

1. SpringBoot sample application
2. Custom field level Validation using javax validation and annotation
3. API documentation using swagger
4. IBM MQ integration to convert JSON request payload to XML and post to IBM MQ.
5. Log configuration using logback.xml
6. Build and deploy the application using docker and docker compose

# Technologies

JDK 11

Springboot 2.2.5

Maven 3.6.1

Swagger 2.9.2

Docker

IBM MQ 9.2.0.0

# Springboot application implementation details

1. Jms Message converter to convert JSON message to XML before posting the message to Queue. @XML* annotation to convert JSON object to XML to post message as XML to IBM MQ.
2. Jms Transcation Management to have the best performane of posting the message to Queue.
3. @JsonIgnore annotation to hide the JSON request fields expose to API integration clients
4. useDefaultResponseMessages(false) on SwaggerConfig to hide the default response in swagger API documentation.
5. apis(RequestHandlerSelectors.basePackage("com.springboot.ibmmq")) to hide "Basic error controller" from API documentatio.
6. Validations at field level to check for required value and against configured values. Refer to "validation" package and interface name annotation in Request.java.
7. Validation on request object is enabled at resource layer using "@Valid" annotation. 
8. The validation runs through the whole request payalod before returning the response rather than failing and returning at first validation error.
9. Incoming request filter and request context concept is implemented to associate a uniqueID to every request, log the uniqueID in server logs and return the value as reference value in the response.
10. Exception handlers for BadRequest and InternalError including JSON Deserialization error.
11. Log management using logback-spring.xml file.

# Build the application docker image

1. Clone the repo from github

2. mvn clean install
    
3. Build the image using below command

    docker build . -t springbootibmmq
