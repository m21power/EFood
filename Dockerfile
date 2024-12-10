# Use an official OpenJDK image as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app
# Copy the .env file into the container
COPY .env /app/.env
# Copy the Maven wrapper and pom.xml
COPY . /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Build the Spring Boot application
RUN ./mvnw clean package

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/EFood-0.0.1-SNAPSHOT.jar"]
