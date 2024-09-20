# Dockerfile
FROM openjdk:17-jdk-slim
COPY ./build/libs/42millionaire-0.0.1-SNAPSHOT.jar /app.jar
COPY ./src/main/resources/keystore.p12 /keystore.p12
ENTRYPOINT ["java", "-jar", "/app.jar"]