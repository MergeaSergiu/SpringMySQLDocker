# SpringMySQLDocker

###
A simple Spring Boot project for learning purpose of using MySQL and Spring Boot with Docker.

###
Steps and commands to use in the project

# docker pull mysql:latest 

docker pull: This command is used to download a Docker image from a Docker registry (typically Docker Hub) to your local machine.
mysql:latest: This specifies the image you want to pull. mysql is the official MySQL Docker image, and latest is the tag that refers to the most recent version of the image.

# docker run --name mysqldbdocker -e MYSQL_ROOT_PASSWORD=sergiu -e MYSQL_DATABASE=mydb -e MYSQL_USER=user -e MYSQL_PASSWORD=root -p 3307:3306 mysql:latest

docker run: This starts a new Docker container from the specified image.

--name mysqldbdocker: This gives your MySQL container the name mysqldbdocker. You can use this name to refer to the container later (e.g., when stopping or starting the container).

-e MYSQL_ROOT_PASSWORD=sergiu: This sets the root password for the MySQL instance to sergiu. This password will be used for the root user to authenticate when accessing MySQL.

-e MYSQL_DATABASE=mydb: This sets the name of the default database to be created when the container is started. In this case, it will create a database named mydb.

-e MYSQL_USER=user: This sets the username for a new user to be created when the container starts. In this case, the new user will be user.

-e MYSQL_PASSWORD=root: This sets the password for the user created in the previous step. In this case, the password will be root.

-p 3307:3306: This maps port 3306 inside the container (the default MySQL port) to port 3307 on your host machine. This means you can access MySQL on localhost:3307 from your host.

mysql:latest: This specifies the image to use. mysql is the official MySQL Docker image, and latest refers to the most recent version of the image.

# (Optional step) Check that Spring Boot Application is running locally with the MySQL container
 - configure the database connection in the application.properties

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost::3307/mydb
spring.datasource.username=user
spring.datasource.password=root

or use

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3307}/mydb
spring.datasource.username=${MYSQL_USER:user}
spring.datasource.password=${MYSQL_PASSWORD:root}

This uses the value of the environment variables. If not set, it uses the default ones

# Configure a Dockerfile

FROM openjdk:17
ADD target/springboot-mysql-docker.jar springboot-mysql-docker.jar
ENTRYPOINT ["java", "-jar", "/springboot-mysql-docker.jar"]

- FROM openjdk:17
Purpose: This line specifies the base image for your Docker image.
What it does: It uses the official OpenJDK 17 image as the base, which contains the necessary Java environment to run your Spring Boot application. This ensures that your app will run in a container with JDK 17 installed.

- ADD target/springboot-mysql-docker.jar springboot-mysql-docker.jar
Purpose: This line copies the Spring Boot JAR file into the Docker image.
What it does: It takes the JAR file built by Maven (presumably located in the target folder) and adds it to the root of the container as springboot-mysql-docker.jar.
Important Note: Make sure you have built the JAR file using mvn clean install or another Maven command before building the Docker image. The ADD command assumes the file is already in the target directory.

- ENTRYPOINT ["java", "-jar", "/springboot-mysql-docker.jar"]
Purpose: This line specifies the command that will be run when the container starts.
What it does: It tells Docker to execute the command java -jar /springboot-mysql-docker.jar inside the container, which will start your Spring Boot application.
java: Runs the Java runtime.
-jar: Tells Java to run the JAR file.
/springboot-mysql-docker.jar: Specifies the JAR file to run (this is the one added in the previous step).

# mvn clean install

mvn: This is the command-line tool for Maven.

clean: This goal removes the target/ directory from your project, which contains the compiled classes, JAR files, and other build artifacts. Essentially, it cleans up any previously compiled files, ensuring a fresh build.

install: This goal compiles your project, runs the tests, and then installs the resulting artifacts (like JAR files) into your local Maven repository. The local repository is typically located in the .m2/ directory in your home folder, and the artifacts are available for use by other projects or dependencies.

# docker build -t spring-mysql-docker-demo .

The command docker build -t spring-mysql-docker-demo . is used to build a Docker image from a Dockerfile in the current directory (denoted by .). Here's a breakdown of the command:

- docker build: This is the Docker command used to build an image from a Dockerfile.

-t spring-mysql-docker-demo: This option specifies the name (or "tag") for the resulting Docker image. In this case, the image will be named spring-mysql-docker-demo.

You can also add a version tag to the image name (e.g., spring-mysql-docker-demo:v1).
.: This is the context for the build, which means the current directory. Docker will look for a Dockerfile in the current directory (or you can specify a different directory if needed).

# docker network create spring-net

The command docker network create spring-net is used to create a custom Docker network called spring-net. Here's a breakdown of the command:

Command Breakdown:
docker network create: This command creates a new network in Docker, which allows containers to communicate with each other.

spring-net: This is the name of the network you are creating. You can choose any name you like for the network.

# docker run -p 9090:8080 --name spring-mysql-connection --net spring-net -e MYSQL_HOST=mysqldb -e MYSQL_USER=root -e MYSQL_PASSWORD=root -e MYSQL_PORT=3306 spring-mysql-docker-demo

Explanation of Parameters:
docker run: This is the command to run a Docker container.

-p 9090:8080: This maps port 8080 inside the container to port 9090 on your host machine. If your Spring Boot app listens on port 8080, you can access it via localhost:9090 from your host.

--name spring-mysql-connection: This gives your container a name, spring-mysql-connection. You can use this name to refer to the container when stopping, starting, or managing it.

--net spring-net: This connects the container to the spring-net network. This is helpful if you have multiple containers (e.g., one for MySQL and one for your Spring Boot app) and want them to communicate with each other on a custom network.

-e MYSQL_HOST=mysqldb: This sets an environment variable named MYSQL_HOST inside the container, with the value mysqldb. This is the hostname or IP address of your MySQL service. The Spring Boot app inside this container will use this variable to know where to find MySQL.

-e MYSQL_USER=root: This sets the MySQL username for the Spring Boot app. The app will use root as the username to connect to the MySQL service.

-e MYSQL_PASSWORD=root: This sets the MySQL password for the Spring Boot app. The app will use root as the password to connect to the MySQL service.

-e MYSQL_PORT=3306: This sets the MySQL port to 3306. By default, MySQL uses port 3306, so the Spring Boot app will use this port to connect to MySQL.

spring-mysql-docker-demo: This is the name of the Docker image you're running. This image should contain your Spring Boot application that needs to connect to MySQL.

