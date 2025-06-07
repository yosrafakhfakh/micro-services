#!/bin/bash

echo "Building Spring Boot application..."
mvn clean package -DskipTests

echo "Building Docker image..."
docker build -t reglement-service .

echo "Starting services..."
docker-compose up -d

echo "Services started successfully!" 