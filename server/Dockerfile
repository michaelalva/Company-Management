# Stage 1: Build the Java application using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean compile

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app .
EXPOSE 8000
CMD ["mvn", "exec:exec"]