# # Dockerfile
# FROM openjdk:17-jdk-slim
# COPY ./build/libs/42millionaire-0.0.1-SNAPSHOT.jar /app.jar
# ENTRYPOINT ["java", "-jar", "/app.jar"]




# # Dockerfile
# FROM gradle:7.5.1-jdk17 AS build
# WORKDIR /app
# COPY --chown=gradle:gradle . /app
# RUN gradle clean build --no-daemon

# FROM openjdk:17-jdk-slim
# COPY --from=build ./build/libs/42millionaire-0.0.1-SNAPSHOT.jar /app.jar
# ENTRYPOINT ["java", "-jar", "/app.jar"]




# Dockerfile

# Build stage using Gradle
FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app
RUN gradle clean build --no-daemon

# Runtime stage using OpenJDK
FROM openjdk:17-jdk-slim
COPY --from=build /app/build/libs/42millionaire-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
