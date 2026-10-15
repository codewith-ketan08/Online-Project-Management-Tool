# Online Project Management Tool (ProjectFlow)

A Java web application starter for managing projects and tasks. It demonstrates Java OOP, Servlets, JDBC, MySQL, DAO classes, collections, validation, and password hashing.

## Features
- User registration and login
- Password hashing with PBKDF2 (passwords are not stored as plain text)
- Dashboard with project/task totals
- Create projects, set dates, update project status, and delete projects
- Create tasks, set priority/due date, update task status, and delete projects/tasks through project deletion
- JDBC DAO classes and prepared statements
- Responsive HTML/CSS interface

## Technology
- Java 17
- Jakarta Servlet 6.0
- Apache Tomcat 10.1+
- MySQL 8+
- Maven

## Setup
1. Install JDK 17, Maven, MySQL 8, and Apache Tomcat 10.1 or newer.
2. Run `database/schema.sql` in MySQL.
3. Set database connection environment variables if needed:
   - `PM_DB_URL` (default `jdbc:mysql://localhost:3306/project_management?useSSL=false&serverTimezone=UTC`)
   - `PM_DB_USER` (default `root`)
   - `PM_DB_PASSWORD` (default blank)
   Do not use a blank password or root account for a real deployment.
4. From the project root, run `mvn clean package`.
5. Copy `target/project-management.war` into Tomcat's `webapps` folder and start Tomcat.
6. Open `http://localhost:8080/project-management/`.
7. Register an account and sign in.

## Architecture
Browser (JSP/HTML/CSS) → Jakarta Servlet → Service / DAO → JDBC → MySQL.

## Important limitations / next steps
This is an academic starter, not a production-ready service. Before public deployment, add CSRF protection, stronger session/cookie configuration, rate limiting, output escaping, role/ownership controls for team assignment, automated tests, and production secret management. Add team assignment and collaboration features before describing them as implemented.

## Rubric mapping
- Problem understanding: README, architecture, database schema, and presentation
- Core Java: model classes, service layer, collections, validation, and exception handling
- JDBC/database integration: `DBConnection`, DAO classes, prepared statements, schema
- Servlet/web integration: login, registration, dashboard, project and task servlets
