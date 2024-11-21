# Virtual Arena Backend 🏛️

<p align="center">
  <img src="https://virtualarena.tech/wp-content/uploads/2023/06/Virtual-Arena-Flaticon.png" alt="Virtual Arena Logo" width="200"/>
  <br>
  <em>Robust backend infrastructure for the Virtual Arena article sharing platform</em>
</p>

## 📋 Overview

Backend service for Virtual Arena, built with Spring Boot 3.x, providing secure API endpoints for article management, user authentication, and community features.

## 🛠️ Technology Stack

### Core
- **Framework**: Spring Boot 3.3.5
- **Language**: Java 17
- **Database**: MongoDB
- **Security**: Spring Security with JWT
- **Documentation**: SpringDoc OpenAPI

### Key Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Data MongoDB
- Spring Boot Starter Mail
- JWT (io.jsonwebtoken)
- Lombok
- SpringDoc OpenAPI UI

## 🔐 Security Features

- JWT-based authentication
- Token refresh mechanism
- Email verification system
- Role-based access control
- Secure password handling

## 📨 Email Integration

- SMTP configuration with Gmail
- Email verification system
- Templated notifications
- Password reset functionality

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- MongoDB
- IDE (IntelliJ IDEA recommended)

### Installation

```bash
# Clone the repository
git clone https://github.com/virtual-arena-platform/virtual-arena-backend.git

# Navigate to project directory
cd virtual-arena-backend

# Install dependencies
mvn clean install

# Run the application
mvn spring-boot:run
```

### Environment Configuration

Create `application.properties` file with the following configurations:

```properties
spring.application.name=virtual-arena-backend
spring.data.mongodb.uri=your_mongodb_uri
spring.data.mongodb.database=your_database_name
application.security.jwt.secret-key=your_jwt_secret
application.security.jwt.expiration=86400000
application.security.jwt.refresh-token.expiration=604800000
application.security.front-url=http://localhost:3000

# Mail Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## 📚 API Documentation

Access the API documentation at `http://localhost:8080/swagger-ui.html` when running locally.

## 🔄 Development Workflow

1. Fork the repository
2. Create feature branch: `git checkout -b feature/YourFeature`
3. Commit changes: `git commit -m 'Add YourFeature'`
4. Push to branch: `git push origin feature/YourFeature`
5. Submit Pull Request

## 👥 Team

- **Konstantine Vashalomidze** - _Initial work_ - [GitHub](https://github.com/KonstantineVashalomidze)

## 🤝 Contributing

Contributions are welcome!

---

<p align="center">
  Made with ❤️ by <a href="https://github.com/KonstantineVashalomidze">Konstantine Vashalomidze</a>
  <br>
  <sub>Part of the Virtual Arena Platform</sub>
</p>