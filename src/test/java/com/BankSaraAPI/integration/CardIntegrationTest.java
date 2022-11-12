package com.BankSaraAPI.integration;

import com.BankSaraAPI.BankSaraApplication;
import com.BankSaraAPI.TestUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BankSaraApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SqlGroup({@Sql("/data.sql")})
public class CardIntegrationTest {

    @LocalServerPort
    private int port;
    private static final String CONTEXT = "account";

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    // new
    @Test
    void should_create_card_to_account() throws IOException {
        var id = "5fd82e4e-c0ae-4771-a9d5-e18e3df32d65";
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/add-new-card-to-account.json", CONTEXT))
                .when().post("/accounts/{id}/cards", id)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    // new
    @Test
    void should_delete_card_from_account() throws IOException {
        var cardId = "ac3a7a5a-1949-4182-b2ca-7f138bd19915";
        given().contentType(ContentType.JSON)
                .when().delete("/cards/{cardId}",cardId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
