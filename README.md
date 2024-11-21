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

Create `.env` file with the following configurations:

```properties
MONGODB_URI=mongodb+srv://username:password@cluster.example.net/
MONGODB_DATABASE=database_name
JWT_SECRET_KEY=your_jwt_secret_key
JWT_EXPIRATION=86400000
JWT_REFRESH_EXPIRATION=604800000
FRONTEND_URL=http://localhost:3000
MAIL_USERNAME=your_email@example.com
MAIL_PASSWORD=your_email_password
```

## 📚 API Documentation

Access the API documentation at `https://virtual-arena-backend.onrender.com/swagger-ui.html` when running locally.

## ⚠️ Important Note
> **First Request Delay**: Since this application is hosted on Render's free tier, the server enters a sleep state after 15 minutes of inactivity. When you make your first request, the server needs to wake up, which can take up to 30-60 seconds. Subsequent requests will work normally.

## 🔄 Development Workflow

1. Fork the repository
2. Create feature branch: `git checkout -b feature/YourFeature`
3. Commit changes: `git commit -m 'Add YourFeature'`
4. Push to branch: `git push origin feature/YourFeature`
5. Submit Pull Request

## 👥 Team

- **Konstantine Vashalomidze** - _Initial work_ - [GitHub](https://github.com/KonstantineVashalomidze)

## 🙏 Acknowledgments

- [Virtual Arena Frontend](https://github.com/virtual-arena-platform/virtual-arena-frontend)
- All our contributors and supporters


---

<p align="center">
  Made with ❤️ by <a href="https://github.com/KonstantineVashalomidze">Konstantine Vashalomidze</a>
  <br>
  <sub>Want to contribute? Feel free to open a PR!</sub>
    <br>
  <sub>Part of the Virtual Arena Platform</sub>
</p>
