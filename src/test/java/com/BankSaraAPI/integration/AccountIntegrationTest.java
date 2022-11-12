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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BankSaraApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SqlGroup({@Sql("/data.sql")})
public class AccountIntegrationTest {

    @LocalServerPort
    private int port;
    private static final String CONTEXT = "account";

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void should_return_all_accounts() throws IOException {
        given()
                .when().get("/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("", equalTo(TestUtils.getPath("response/get-all-accounts-response.json", CONTEXT).get("")));
    }

    @Test
    void should_add_new_account_when_currency_is_not_provided() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/add-new-account-request-without-currency.json", CONTEXT))
                .when().post("/accounts")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void should_add_new_account_with_EUR_currency_when_currency_is_not_provided() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/add-new-account-request-with-EUR-currency.json", CONTEXT))
                .when().post("/accounts")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void should_change_the_balance_in_savings_from_0_to_1000() throws IOException {
        var id = "5fd82e4e-c0ae-4771-a9d5-e18e3df32d65";
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/change-the-balance.json", CONTEXT))
                .when().put(String.format("/accounts/%s/balance", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("", equalTo(TestUtils.getPath("response/change-the-balance-of-savings.json", CONTEXT).get("")));
    }

    @Test
    void should_change_the_currency_from_PLN_to_EUR() throws IOException {
        var id = "fc35ad28-91fc-449b-af3e-918417266f9d";
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/change-the-currency.json", CONTEXT))
                .when().put(String.format("/accounts/%s/currency", id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().body("", equalTo(TestUtils.getPath("response/change-the-currency-of-main.json", CONTEXT).get("")));
    }

    @Test
    void should_delete_GBP_account() {
        var id = "c7c6c077-931e-42f9-982a-8c836ab6b932";
        given().contentType(ContentType.JSON)
                .when().delete("/accounts/{%s}", id)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void should_transfer_50_from_EUR_account_to_USD_account() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void should_not_transfer_when_id_is_incorrect() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer-incorrect-id.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void should_not_transfer_when_balance_is_under_zero() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer-incorrect-balance.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .and().body("message", is("Account balance is too low"));
    }

    @Test
    void should_not_transfer_when_amount_is_under_zero() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer-incorrect-amount.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .and().body("message", is("Cannot be less than zero"));
    }

    @Test
    void should_not_transfer_20_from_GBP_account_to_CHF_account() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer-incorrect-GBP-to-CHF.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .and().body("message", is("Transfer is not possible"));
    }

}

