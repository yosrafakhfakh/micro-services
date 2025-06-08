# Dockerfile pour config-service
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM eclipse-temurin:17-jre
COPY --from=build /app/target/config-service-0.0.1-SNAPSHOT.jar ./config-service.jar
HEALTHCHECK --interval=10s --timeout=3s --start-period=10s CMD curl -f http://localhost:5555/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/config-service.jar"]
