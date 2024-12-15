FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTest

FROM openjdk:17.0.1-jdk-slim
COPY --from=build target/virtual-arena-backend-0.0.1-SNAPSHOT.jar virtual-arena-backend.jar
EXPOSE 10004
ENTRYPOINT ["java", "-jar", "virtual-arena-backend.jar"]