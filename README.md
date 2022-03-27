# Storage app
This repository contains code of storage application.
* Made by Aleksandr Aleksandrov


## Short overview

This repository contains a project structure and all implemented functionality of back and front sides of storage app.

The project uses Spring Boot/Angular and can be built/deployed with Gradle.

## Database
Database configuration could be founded in `src/main/resources/application.properites`

To run application you need to configure PostgreSQL database with following instructions:

1. Create user
``
CREATE USER ale WITH password 'ale';
``
2. Create storagedb database 
``
CREATE DATABASE storagedb;
``
3. Grant all privileges to user
``
GRANT ALL PRIVILEGES ON DATABASE storagedb TO ale;
``

## Logging

This project uses external slf4j lombok logging tool. 

All logs are saved in gitignored `log` folder

## API Endpoints

Proceed to `http://localhost:8080/swagger-ui.html` for swagger API documentation.

## Testing

Type `./gradlew test (gradlew test on windows)` for running basic test cases