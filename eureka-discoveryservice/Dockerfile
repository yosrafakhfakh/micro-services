# Builder
FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
# Image finale
FROM eclipse-temurin:17-jre

COPY --from=build /app/target/eureka-discoveryservice-0.0.1-SNAPSHOT.jar ./eureka-discoveryservice.jar

ENTRYPOINT ["java","-jar","/eureka-discoveryservice.jar"]
