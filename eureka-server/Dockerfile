FROM maven:3.9.5-eclipse-temurin-21
LABEL authors="garikek"

COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]