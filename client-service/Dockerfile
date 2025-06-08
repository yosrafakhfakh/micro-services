# Étape 1 : builder l'application avec Maven + JDK 17
FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests
# Étape 2 : image finale avec JRE + netcat installé
FROM eclipse-temurin:17-jre
COPY --from=build /app/target/client-service-0.0.1-SNAPSHOT.jar /client-service.jar
ENTRYPOINT ["java","-jar","/client-service.jar"]