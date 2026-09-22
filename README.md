# Virtual Arena Backend

Backend service for Virtual Arena, an article-sharing platform. Built with Spring Boot, it handles authentication, article management, and the community features the frontend depends on.

## Tech stack

Spring Boot 3.3.5, Java 17, MongoDB, Spring Security with JWT, and SpringDoc OpenAPI for the API docs. Other dependencies include Spring Mail, io.jsonwebtoken, and Lombok.

## Security

JWT-based authentication with refresh tokens, email verification on signup, role-based access control, and hashed passwords.

## Email

SMTP through Gmail handles verification emails, notifications, and password resets.

## Running locally

You will need Java 17 or higher, Maven, and MongoDB.

```
git clone https://github.com/virtual-arena-platform/virtual-arena-backend.git
cd virtual-arena-backend
mvn clean install
mvn spring-boot:run
```

Create a .env file with:

```
MONGODB_URI=mongodb+srv://username:password@cluster.example.net/
MONGODB_DATABASE=database_name
JWT_SECRET_KEY=your_jwt_secret_key
JWT_EXPIRATION=86400000
JWT_REFRESH_EXPIRATION=604800000
FRONTEND_URL=http://localhost:3000
MAIL_USERNAME=your_email@example.com
MAIL_PASSWORD=your_email_password
```

## API docs

Swagger UI is available at /swagger-ui.html once the app is running.

## Hosting note

This runs on Render's free tier, so it sleeps after 15 minutes without traffic. The first request after that can take 30 to 60 seconds while it wakes up; after that it responds normally.

## Related

Frontend: https://github.com/virtual-arena-platform/virtual-arena-frontend

Author: Konstantine Vashalomidze
