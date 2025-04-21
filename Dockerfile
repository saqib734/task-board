# Stage 1: Build stage (if needed, can be merged if just running a JAR)
FROM openjdk:17.0.1-jdk-slim as builder

WORKDIR /app
COPY build/libs/task-board-0.0.1-SNAPSHOT.jar app.jar

# Stage 2: Use JDK Alpine again
FROM openjdk:17.0.1-jdk-slim

WORKDIR /app
COPY --from=builder /app/app.jar .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]