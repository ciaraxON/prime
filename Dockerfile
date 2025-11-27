# Dockerfile
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# copy sources and build
COPY . .
RUN mvn -B -DskipTests clean package

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# copy the built jar (wildcard ok if only one)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
