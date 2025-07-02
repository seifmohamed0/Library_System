# Library Management System

## Overview
A Spring Boot application for managing a library with role-based access control.

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
  - Can view borrowing transactions and return books only.
  - No modification rights.
  - Example: Access to `/api/borrows` with read-only permissions.
    
## Security Annotations in Code

- `@PreAuthorize("hasRole('ADMIN')")` restricts access to ADMIN users.
- Public endpoints (e.g., `GET /api/books`) are accessible by all.
- Authentication endpoints (`/api/auth/register`, `/api/auth/login`) are open to all users.

---
