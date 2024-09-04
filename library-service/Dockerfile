FROM maven:3.9.5-eclipse-temurin-21
LABEL authors="garikek"

COPY target/library-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]