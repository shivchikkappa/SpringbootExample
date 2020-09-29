# Technologies
JDK 11

Springboot 2.2.5

SpringbootAdmin 2.1.6

Maven 3.6.1

Docker

# Build and Run the application without docker

1. Clone the repo from github 

2. mvn clean install

3. java -jar target/springbootadmin-0.0.1-SNAPSHOT.jar

4. Application will start running on port 9060

5. Admin UI can be accessed at http://localhost:9060/login 

# Build the application docker image

1. Clone the repo from github

2. mvn clean install
    
3. Build the image using below command

    docker build . -t myspringbootadmin
    
# Run the application using docker image

1. Start the container using below command 
    
    docker run -d --restart unless-stopped --name myspringbootadmin -p 9060:9060 -p 9065:9065 myspringbootadmin
    
2. Give a moment for container to start and check if it's running using below command

    docker container ls
    
    ad1c0362b09f        myspringbootadmin        "java -jar /springbo…"   25 seconds ago      Up 24 seconds       0.0.0.0:9060->9060/tcp, 0.0.0.0:9065->9065/tcp   myspringbootadmin

3. Stop and remove the container using below command

    docker container stop myspringbootadmin
    docker container rm myspringbootadmin
