E-Commerce Project
This project is an e-commerce application built with Java Spring Boot, MySQL, and JPA, with Lombok for reducing boilerplate code. It supports essential e-commerce functionalities, including product management, user authentication, and cart operations.

Features:
User Registration and Authentication: User can register, log in, and manage their profiles.
Product Management: CRUD operations for product management, with categories and inventory.
Shopping Cart: Users can add products to their cart, update quantities, and remove items.
Order Processing: Process orders, including order history and status tracking.
Admin Panel: Manage users, products, and orders with administrative privileges.
Search and Filter: Search products by name or filter by category.
Error Handling and Validation: Input validation and exception handling for a robust user experience.

Tech Stack:
Backend: Java Spring Boot
Database: MySQL
ORM: JPA (Java Persistence API)
Other Libraries:
Lombok: To reduce boilerplate code like getters, setters, etc.
Spring Security: For user authentication and authorization.
Hibernate: JPA implementation for ORM.
Getting Started
Prerequisites
Java 11 or later
MySQL 8.0 or later
Maven or Gradle
Installation
Clone the Repository:


git clone https://github.com/your-username/e-commerce-project.git
cd e-commerce-project


Configure MySQL Database:
Create a database named e_commerce in MySQL.
Update application.properties or application.yml with your MySQL username and password.

properties:
spring.datasource.url=jdbc:mysql://localhost:3306/e_commerce
spring.datasource.username=your-username
spring.datasource.password=your-password


Build and Run the Project:
./mvnw spring-boot:run

or for Gradle:
./gradlew bootRun
Access the application at http://localhost:8080.
