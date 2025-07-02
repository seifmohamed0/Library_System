# Library Management System

## Overview
A Spring Boot application for managing a library with role-based access control.

## Setup Instructions

- **Before running the project**, you must create a MySQL database named **`library_db`.**
- The application expects this database to exist and will not create it automatically.
- This SQL dump file (`library_full_dump.sql` included in the project resources.) contains all necessary commands to set up tables and seed initial data.

## Roles & Authorities

- **ADMIN**  
  - Full access to the system.
  - Can manage users, books, members, and view activity logs.
  - Examples: Access to `GET /api/activity-logs`, full CRUD on users and books.

- **LIBRARIAN**  
  - Can manage books, members, and borrowing transactions.
  - Allowed to add, update, and delete books; handle borrow/return operations.
  - Examples: CRUD on `/api/books`, borrowing via `/api/borrows`.

- **STAFF**  
  - Can view borrowing transactions and return books.
  - No modification rights.
  - Example: Access to `/api/borrows` with read-only permissions.

## Members
- Members represent library users who borrow books.
- Roles like LIBRARIAN and ADMIN manage members and their borrowing transactions.
- Members do not have direct access to system management endpoints but are the main entities borrowing books.

# Project Structure Overview

├── Library_ERD.PNG # Database Entity-Relationship Diagram
├── Postman # Postman collections and environment files  
│   ├── code81Library.postman_collection.json  
│   └── Library API.postman_environment.json  
├── src  
│   ├── main  
│   │   ├── java  (Java source code)  
│   │   └── resources  
│   │       ├── application.properties # Spring Boot config file  
│   │       └── library_full_dump.sql # SQL script to initialize and populate the database with sample data

## Pre-Initialized Users

The database is pre-populated with the following user accounts via the `library_full_dump.sql` file.  
These users are ready to use for testing purposes.

| Username  | Role      | Password |
|-----------|-----------|----------|
| admin     | Admin     | 0000     |
| staff     | Staff     | 0000     |
| librarian | Librarian | 0000     |

You can use these credentials to log in and test the application features according to the assigned roles.

## Technologies & Dependencies

| Technology / Library          | Purpose                                 |
|------------------------------|-----------------------------------------|
| **Spring Boot Starter Web**  | Build RESTful APIs with Spring MVC      |
| **Spring Boot Starter Data JPA** | Database access with JPA & Hibernate     |
| **Spring Boot Starter Security** | Application security and JWT handling    |
| **MySQL Connector/J**        | MySQL database driver                    |
| **jjwt (JSON Web Token)**    | JWT creation and verification            |
| **Hibernate Validator**      | Data validation annotations              |
| **SpringDoc OpenAPI**        | Auto-generate API docs (Swagger UI)     |

## Java & Build

- **Java Version:** 21  
- **Build Tool:** Maven  
- **Maven Plugins:**  
  - `maven-enforcer-plugin` (dependency version enforcement)  
  - `maven-compiler-plugin` (Java compilation config)  
  - `spring-boot-maven-plugin` (build and run Spring Boot app)  

