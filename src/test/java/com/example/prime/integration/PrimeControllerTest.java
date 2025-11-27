package com.example.prime.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Disabled("Integration tests disabled until RestAssured/Jackson3 compatibility issue is resolved")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrimeControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void happyPathReturnsJson() {
        given()
                .accept(ContentType.JSON)
                .queryParam("limit", 10)
                .when()
                .get("/api/primes")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("limit", equalTo(10))
                .body("primes[0]", equalTo(2));
    }

    @Test
    void missingLimitReturnsBadRequestWithCannotBeNullMessage() {
        given()
                .when()
                .get("/api/primes")
                .then()
                .statusCode(400)
                .body(equalTo("cannot be null"));
    }

    @Test
    void nonIntegerLimitReturnsMustBeAnInteger() {
        given()
                .queryParam("limit", "abc")
                .when()
                .get("/api/primes")
                .then()
                .statusCode(400)
                .body(equalTo("must be an integer"));
    }

    @Test
    void xmlFormatReturnsXml() {
        given()
                .accept("application/xml")
                .queryParam("limit", 10)
                .when()
                .get("/api/primes")
                .then()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body(containsString("<limit>10</limit>"))
                .body(containsString("<prime>2</prime>"));
    }

    @Test
    void uiEndpointServesHtml() {
        given()
                .when()
                .get("/api/primes/ui")
                .then()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body(containsString("<title>Prime Generator UI</title>"));
    }
}
