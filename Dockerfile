FROM maven:3.8.4-openjdk-17-slim as builder

WORKDIR /app

COPY . .

RUN mvn clean install

WORKDIR /app

COPY --from=builder ./app/target/auth-0.0.1-SNAPSHOT.jar .

FROM openjdk:17-jdk-slim

EXPOSE 8080

ENTRYPOINT ["java", "-jar"]

CMD ["auth-0.0.1-SNAPSHOT.jar"]

