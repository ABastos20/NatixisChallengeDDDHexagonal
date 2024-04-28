# Committee Management System

This project is a Java-based Spring Boot application that provides an API to manage problems and committees
according to the principles of Hexagonal architecture, clean architecture, and Domain-Driven Design (DDD).
It offers functionalities for creating and managing problems that are grouped into committees, which can be scheduled, started, and closed.

## Description

The Committee Management System is designed to handle "problems" that are discussed in scheduled "committees".
Problems can be opened, commented upon, and once a comment is added, they are ready to be attached to a committee.
Each committee has a set date and might include multiple problems to address during its session.
The system ensures proper state transitions of problems and committees, providing a clear workflow and secured data access.

## Prerequisites

Ensure you have the following installed before running the application:
- Java Development Kit (JDK) 17 or higher
- Apache Maven

## Technology Stack

- **Spring Boot**: Server-side framework
- **H2 Database**: Embedded in-memory database for rapid prototyping and testing
- **JUnit & Mockito**: For unit and integration tests
- **Springdoc OpenAPI**: For API documentation

## Features

- **Manage Problems**: Open new problems, add comments, and prepare them for committee discussions.
- **Manage Committees**: Schedule, start, and close committees, each potentially holding multiple problems.
- **API Documentation**: Auto-generated documentation using Springdoc OpenAPI.

## Getting Started

Follow these steps to get the project up and running:

## Build the project using Maven:

 mvn clean package

## Run the application:

mvn spring-boot:run

or

java -jar target/DDDHexagonalChallenge-0.0.1-SNAPSHOT.jar

# API Documentation

Access the Swagger UI for the API documentation at http://localhost:8080/api after starting the application.

# API Endpoints

## Problem Web Port

**GET /api/problems**: List all problems in the system.

**POST /api/problems**: Create a new problem.

**POST /api/problems/{problemName}/comments**: Add a comment to a problem.

**POST /api/problems/{problemName}/committees/{committeeDate}**: Add a Problem to a Committee.

## Committees Web Port

**GET /api/committees**: List all committees in the system.

**POST /api/committees**: Schedule a new committee.

**PUT /api/committees/{committeeDate}/start**: Start a committee.

**PUT /api/committees/{committeeDate}/close**: Close a committee.

## Usage:

### Problem Web Port

GET /api/problems: List all problems in the system.
curl -X 'GET' \
  'http://localhost:8080/api/problems' \
  -H 'accept: */*'

POST /api/problems: Create a new problem.
curl -X 'POST' \
  'http://localhost:8080/api/problems' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "string"
}'

POST /api/problems/{problemName}/comments: Add a comment to a problem.
curl -X 'POST' \
  'http://localhost:8080/api/problems/*Problem Name*/comments' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d 'string'

POST /api/problems/{problemName}/committees/{committeeDate}: Add a Problem to a Committee.
curl -X 'POST' \
  'http://localhost:8080/api/problems/*Problem Name*/committees/*DATE format yyyy-mm-dd*' \
  -H 'accept: */*' \
  -d ''

### Committees Web Port

GET /api/committees: List all committees in the system.
curl -X 'GET' \
  'http://localhost:8080/api/committees' \
  -H 'accept: */*'

POST /api/committees: Schedule a new committee.
curl -X 'POST' \
  'http://localhost:8080/api/committees' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "date": "*DATE format yyyy-mm-dd*"
}'

PUT /api/committees/{committeeDate}/start: Start a committee.
curl -X 'PUT' \
  'http://localhost:8080/api/committees/start?date=*DATE format yyyy-mm-dd*' \
  -H 'accept: */*'

PUT /api/committees/{committeeDate}/close: Close a committee.
curl -X 'PUT' \
  'http://localhost:8080/api/committees/close?date=*DATE format yyyy-mm-dd*' \
  -H 'accept: */*'

# Testing
To run unit tests :
 
mvn clean test

## Contact

For queries or suggestions, contact the project maintainer at abastosvlc@gmail.com.