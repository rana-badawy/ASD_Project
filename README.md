# Surgeries Management System

A Spring Boot-based web application for managing surgical procedures and related operations.

## Project Overview

This project is a comprehensive surgical management system built with Spring Boot, providing a robust backend for handling surgical procedures, patient information, and related operations.

## Technologies Used

- **Java 24**
- **Spring Boot 3.4.4**
- **Spring Security**
- **Spring Data JPA**
- **MySQL Database**
- **MapStruct** (for object mapping)
- **Lombok** (for reducing boilerplate code)
- **JWT** (for authentication)
- **Maven** (for dependency management)

## Features

- RESTful API endpoints
- JWT-based authentication
- Database integration with MySQL
- Object mapping with MapStruct
- Spring Security implementation
- Comprehensive testing support

## Business Requirements

### User Stories

#### Patient Management
- Hospital administrator should be able to register new patients so that their information is stored in the system
- Hospital administrator should be able to update patients information
- Hospital administrator should be able to delete patients records
- Medical staff member should be able to view patient medical history to make informed decisions about surgical procedures

#### Appointments Management
- Hospital administrator should be able to schedule appointments
- Hospital administrator should be able to update or delete appointments
- Dentists should be able to view the scheduled appointments

#### Resource Management
- Hospital administrator should be able to add, update, and delete surgeries data
- Hospital administrator should be able to add, update, and delete dentists data

#### Security and Access Control
- Hospital administrator should be able to manage patients, dentists, surgeries, and appointments data
- Dentists are only allowed to view the data

## Prerequisites

- Java 24 or higher
- Maven 3.x
- MySQL Server
- Your favorite IDE (IntelliJ IDEA recommended)

## Getting Started

1. Clone the repository:
   ```bash
   git clone [repository-url]
   ```

2. Configure the database:
   - Create a MySQL database
   - Update the database configuration in `application.properties`

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```


## Database Schema

The project includes a MySQL Workbench model file (`surgeries_db_schema.mwb`) that defines the database structure.

## Security

The application implements Spring Security with JWT-based authentication for secure access to the API endpoints.

## Testing

The project includes comprehensive testing support with:
- JUnit 5
- Spring Test
- H2 Database for testing