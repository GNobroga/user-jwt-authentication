FROM maven:3.8.4-openjdk-17-slim as builder

COPY . .

RUN mvn clean install

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder ./core/target/core-1.0-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "core-1.0-SNAPSHOT.jar"]



