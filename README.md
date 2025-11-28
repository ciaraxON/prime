# Technical Assessment - Backend Developer

See deployed code at: https://prime-1-k912.onrender.com/api/primes/ui

## Outline
Write a RESTful service which calculates and returns all the prime numbers up to and including a number provided.

## Requirements:

### The project must be written in Java 17 or 20;

[`pom.xml` (Java 17)](https://github.com/ciaraxON/prime/blob/main/pom.xml)

### The project must use Maven OR Gradle to build, test and run;

#### This project uses Maven. See [`pom.xml`](pom.xml)

- Use the Maven Wrapper (preferred):
    - `./mvnw clean package`
    - `./mvnw spring-boot:run`
    - `./mvnw test`
- Or system Maven:
    - `mvn clean package`
    - `mvn spring-boot:run`
    - `mvn test`

### The project must have unit (JUnit) and integration (Restassured or Karate) tests;

- Unit tests directory: [`src/test/java/com/example/prime/unit/`](https://github.com/ciaraxON/prime/tree/main/src/test/java/com/example/prime/unit/)
  - Examples:
    - [`src/test/java/com/example/prime/unit/SieveOfEratosthenesTest.java`](https://github.com/ciaraxON/prime/blob/main/src/test/java/com/example/prime/unit/SieveOfEratosthenesTest.java)
    - [`src/test/java/com/example/prime/unit/TrialDivisionAlgorithmTest.java`](https://github.com/ciaraxON/prime/blob/main/src/test/java/com/example/prime/unit/TrialDivisionAlgorithmTest.java)
    - [`src/test/java/com/example/prime/unit/AlgorithmSelectorTest.java`](https://github.com/ciaraxON/prime/blob/main/src/test/java/com/example/prime/unit/AlgorithmSelectorTest.java)
    - [`src/test/java/com/example/prime/unit/PrimeServiceTest.java`](https://github.com/ciaraxON/prime/blob/main/src/test/java/com/example/prime/unit/PrimeServiceTest.java)

- Application configuration test: [`src/test/java/com/example/prime/PrimeApplicationTests.java`](https://github.com/ciaraxON/prime/blob/main/src/test/java/com/example/prime/PrimeApplicationTests.java)

- Integration tests (currently disabled): [`src/test/java/com/example/prime/integration/PrimeControllerTest.java`](https://github.com/ciaraxON/prime/blob/main/src/test/java/com/example/prime/integration/PrimeControllerTest.java) — disabled with `@Disabled("Integration tests disabled until RestAssured/Jackson3 compatibility issue is resolved")`


### The project must be built upon Spring Boot;

[`pom.xml`](pom.xml) uses the Spring Boot parent and the `spring-boot-maven-plugin`:

### The API must respond with valid JSON;

See deployed code at: https://prime-1-k912.onrender.com/api/primes/ui

### The API must be appropriately (to your discretion) documented;

### You may use any other frameworks or libraries for support e.g. Lombok, Rest Assured etc.;

- See `pom.xml` for declared dependencies and build plugins: [`pom.xml`](https://github.com/ciaraxON/prime/blob/main/pom.xml)
- Integration testing: project uses RestAssured — see `src/test/java/com/example/prime/integration/PrimeControllerTest.java` ([file](https://github.com/ciaraxON/prime/blob/main/src/test/java/com/example/prime/integration/PrimeControllerTest.java)).
- XML support: Jackson 3 XML is included and used by the response model — see `pom.xml` and `src/main/java/com/example/prime/model/PrimeResponse.java` ([file](https://github.com/ciaraxON/prime/blob/main/src/main/java/com/example/prime/model/PrimeResponse.java)).

### The project must be accessible from Github – if you do not wish to make it public, please add my user to the repository: Your Github account name.

https://github.com/ciaraxON/prime

## Optional Extensions:

### Deploy the solution to a chosen platform that we can access;

See deployed code at: https://prime-1-k912.onrender.com/api/primes/ui

### Consider supporting varying return content types such as XML based, that should be configurable using the requested media type;

- The API supports JSON and XML. Clients may request the response format using either:
  - the `Accept` header (e.g. `application/json` or `application/xml`), or
  - the `format` query parameter (`?format=json` or `?format=xml`).

- Examples:
  - JSON via Accept header:
    ```bash
    curl -s -H "Accept: application/json" "http://localhost:8080/api/primes?limit=10"
    ```
  - XML via Accept header:
    ```bash
    curl -s -H "Accept: application/xml" "http://localhost:8080/api/primes?limit=10"
    ```
  - XML via query parameter:
    ```bash
    curl -s "http://localhost:8080/api/primes?limit=10&format=xml"
    ```

- Implementation notes:
  - Content negotiation is configured in `src/main/java/com/example/prime/config/FormatConfig.java` (maps `format` param to `json`/`xml`).
  - Controller produces both media types: `src/main/java/com/example/prime/controller/PrimeController.java`.
  - XML serialization uses Jackson XML annotations on the response model: `src/main/java/com/example/prime/model/PrimeResponse.java`.
  - Dependency providing XML support: `tools.jackson.dataformat:jackson-dataformat-xml` in `pom.xml`.

- Tests:
  - Integration tests demonstrating JSON/XML are in `src/test/java/com/example/prime/integration/PrimeControllerTest.java` but are currently disabled due to a RestAssured/Jackson3 compatibility issue. Re-enable after resolving that compatibility problem.

### Consider ways to improve overall performance e.g. caching results, concurrent algorithm;

This project includes several implemented optimisations to improve throughput and reduce repeated work:

- Caching
  - Implemented via Spring Cache and a simple in-memory store.
  - See `src/main/java/com/example/prime/config/CacheConfig.java` and the `@Cacheable` annotation on `src/main/java/com/example/prime/service/PrimeService.java` (cache name `primeResults`).
  - Cache key: string of `limit` + `-` + algorithm name (or `DEFAULT`).
 
- Concurrent / parallel algorithms
  - `ParallelAlgorithm` (`src/main/java/com/example/prime/service/algorithm/ParallelAlgorithm.java`) uses parallel processing of base primes to mark multiples for faster execution on multi-core machines.
  - `SegmentedSieve` (`src/main/java/com/example/prime/service/algorithm/SegmentedSieve.java`) reduces memory footprint by processing ranges in segments and is suitable for very large limits.
  - Both implementations are registered as Spring services and selected via `AlgorithmSelector` (`src/main/java/com/example/prime/service/AlgorithmSelector.java`).

- Automatic algorithm selection and tuning
  - `AlgorithmSelector` chooses the best algorithm based on the requested `limit` using configurable thresholds defined in the class.

### Consider supporting multiple algorithms that can be switched based on optional parameters.

API parameter: Clients may request a particular algorithm using the optional query parameter `algorithm`. Accepted values are the `AlgorithmType` enum names (e.g. `TRIAL_DIVISION`, `SIEVE_OF_ERATOSTHENES`, `PARRALEL_ALGORITHM`, `SEGMENTED_SIEVE`). If omitted the server uses automatic selection based on `limit`.

- Examples:
  - Force trial division (JSON):
    ```bash
    curl -s -H "Accept: application/json" "http://localhost:8080/api/primes?limit=100&algorithm=TRIAL_DIVISION"
    ```
  - Force segmented sieve (XML):
    ```bash
    curl -s -H "Accept: application/xml" "http://localhost:8080/api/primes?limit=1000000&algorithm=SEGMENTED_SIEVE"
    ```
  - Auto selection (no `algorithm` param):
    ```bash
    curl -s "http://localhost:8080/api/primes?limit=5000"
    ```

- Implementation notes:
  - Controller parameter: `PrimeController` accepts `@RequestParam(name="algorithm", required=false) AlgorithmType algorithm` and forwards it to `PrimeService`.
  - Mapping: `AlgorithmMapping` collects all `PrimeAlgorithm` beans and maps them by `AlgorithmType`.
  - Default/auto: `AlgorithmSelector` chooses an algorithm when `algorithm` is `null`.
  - Add a new algorithm: implement `PrimeAlgorithm`, annotate with `@Service`, add corresponding `AlgorithmType` enum value, and ensure UI/docs use the exact enum name.

## Deploy locally

1. Fork the repository on GitHub (upstream: `https://github.com/ciaraxON/prime`) and clone your fork:

```bash
git clone https://github.com/<your-github-username>/prime.git
cd prime
```
2. Build and run the application using the Maven Wrapper:

- Windows

```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
mvnw.cmd test
```

- Linux / MacOS

```bash
./mvnw clean install
./mvnw spring-boot:run
./mvnw test
```
- or using system Maven:

```bash
mvn clean install
mvn spring-boot:run
mvn test
```

The application will start on `http://localhost:8080`

UI Endpoint: http://localhost:8080/api/primes/ui
