# Build stage: Build the application using Maven
FROM maven:3.8.1-openjdk-17-slim AS Build

WORKDIR /apps

# Copy the pom.xml and install dependencies offline
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copy the application source code and build the JAR file
COPY src ./src

RUN mvn clean package -DskipTests

# Deployment stage: Use OpenJDK for running the JAR file
FROM openjdk:17.0.1-jdk-slim AS Deploy

# Set the working directory for the deployed application
WORKDIR /app

# Copy the JAR file from the Build stage into the container
COPY --from=Build /apps/target/*.jar /app/app.jar

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Run the application using the JAR file
CMD ["java", "-jar", "/app/app.jar"]
