# 🛒 Simple E-Commerce Platform (Microservices-Based)

This is a **simple e-commerce platform** built using the **Spring Boot framework** and designed using the **Microservices Architecture Pattern** inspired by Microsoft’s best practices. The project is structured as a monorepo with 5 independent services, each responsible for a specific domain of the platform.

---

## 🧱 Project Structure

ecommerce-platform/
├── user-service/
├── product-service/
├── order-service/
├── invoice-service/
├── email-service/
└── README.md

---

## 🔧 Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- REST APIs
- Maven
- MySQL (via Docker)
- MariaDB (via Docker)
- MongoDB (via Docker)
- Global Exception Handling

---

## 🧩 Microservices Overview

| Service          | Responsibility |
|------------------|----------------|
| **User Service**     | Handles user registration, login, and user profile management. |
| **Product Service**  | Manages product creation, update, listing, and availability. |
| **Order Service**    | Handles order creation and updates product quantities accordingly. |
| **Invoice Service**  | Generates and stores invoice records based on completed orders. |
| **Email Service**    | Sends order confirmation and invoice emails to users. |

---

## 🚀 How to Run the Project

### 📦 Prerequisites
- Java 17+
- Maven
- Docker
- Git

🛠️ Features Implemented
 Clean layered architecture using DTOs, Services, Controllers

 Microservice communication with proper separation of concerns

 Product stock adjustment on order creation

 Global exception handling across services

 Dockerized MySQL database

 Email notifications via EmailService

📌 Future Improvements
Add Spring Security with JWT authentication

Integrate async communication (e.g., RabbitMQ or Kafka)

Add frontend UI (React/Angular/React Native)

Containerize all services with Docker Compose

Implement CI/CD pipeline

🙋‍♂️ Author
Pubudu Karunanayake
📚 Electronics and Computer Science Undergraduate
🌍 University of Kelaniya, Sri Lanka
💼 Aspiring Software Engineer
