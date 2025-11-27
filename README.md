## Technical Assessment - Backend Developer

## Outline
Write a RESTful service which calculates and returns all the prime numbers up to and including a number provided.

## Requirements:

### The project must be written in Java 17 or 20;

[`pom.xml` (Java 17)](https://github.com/ciaraxON/<repo>/blob/main/pom.xml)

### The project must use Maven OR Gradle to build, test and run;

### This project uses Maven. See [`pom.xml`](pom.xml)

- Use the Maven Wrapper (preferred):
    - `./mvnw clean package`
    - `./mvnw spring-boot:run`
    - `./mvnw test`
- Or system Maven:
    - `mvn clean package`
    - `mvn spring-boot:run`
    - `mvn test`

### The project must have unit (JUnit) and integration (Restassured or Karate) tests;

### The project must be built upon Spring Boot;

[`pom.xml`](pom.xml) uses the Spring Boot parent and the `spring-boot-maven-plugin`:

### The API must respond with valid JSON;

See deployed code at: 

### The API must be appropriately (to your discretion) documented;

### You may use any other frameworks or libraries for support e.g. Lombok, Rest Assured etc.;

### The project must be accessible from Github – if you do not wish to make it public, please add my user to the repository: Your Github account name.

## Optional Extensions:

### Deploy the solution to a chosen platform that we can access;

### Consider supporting varying return content types such as XML based, that should be configurable using the requested media type;

### Consider ways to improve overall performance e.g. caching results, concurrent algorithm;

### Consider supporting multiple algorithms that can be switched based on optional parameters.