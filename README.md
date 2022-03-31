# Storage app
This repository contains code of storage application.
* Made by Aleksandr Aleksandrov


## Short overview

This repository contains a project structure and all implemented functionality of back and front sides of storage app.

The project uses Spring Boot/Angular and can be built/deployed with Gradle.

Front-end side located under `app-ui` submodule

Pre-create user's login data could be changed in `src/main/resources/admin.properties`

(check AppConfig class for better understanding how it works in application)

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

## Run

Whole application (FE + BE) is running on port :8080

```
gradlew bootRun - Windows

./gradlew bootRun - Linux
```
## Logging

This project uses external slf4j lombok logging tool. 

All logs are saved in gitignored `log` folder

## API Endpoints

Proceed to `http://localhost:8080/swagger-ui.html` for swagger API documentation.

## Testing
In total there are ~24 test cases for back end side.

You can type `./gradlew testEngine` for filling UI with test data. 

It will add some items to admin user

!NB make sure application is running!

Type `./gradlew test (gradlew test on windows)` for running basic test cases