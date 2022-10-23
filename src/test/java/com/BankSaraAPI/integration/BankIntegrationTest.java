package com.BankSaraAPI.integration;

import com.BankSaraAPI.BankSaraApplication;
import com.BankSaraAPI.TestUtils;
import com.BankSaraAPI.model.Account;
import com.BankSaraAPI.model.Currency;
import com.BankSaraAPI.repository.BankRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(classes = {BankSaraApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankIntegrationTest {

    @LocalServerPort
    private int port;
    // zdefiniowanie stringa aby nie powtarzać za każdym razem w ścieżce
    private static final String CONTEXT = "account";

    @Autowired
    private BankRepository bankRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        // włącz rejestrowanie żądania i odpowiedzi
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        bankRepository.deleteAll();
        bankRepository.save(new Account(UUID.fromString("fc35ad28-91fc-449b-af3e-918417266f9d"), "main account", 10000.10, Currency.PLN));
        bankRepository.save(new Account(UUID.fromString("5fd82e4e-c0ae-4771-a9d5-e18e3df32d65"), "savings account", 0, Currency.PLN));
        bankRepository.save(new Account(UUID.fromString("5f73cec7-6ac1-46ee-a203-794c35d8800c"), "EUR account", 50, Currency.EUR));
        bankRepository.save(new Account(UUID.fromString("3d9c2f62-d66c-4e31-89a7-ccdf271a7591"), "USD account", 50, Currency.USD));
        bankRepository.save(new Account(UUID.fromString("c7c6c077-931e-42f9-982a-8c836ab6b932"), "GBP account", 250.5, Currency.PLN));
    }

    // wyświetlanie wszystkich kont
    @Test
    void should_return_all_accounts() throws IOException {
        given()
                .when().get("/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("", equalTo(TestUtils.getPath("response/get-all-accounts-response.json", CONTEXT).get("")));
    }

    // stworzenie nowego konta
    @Test
    void should_add_new_account_when_currency_is_not_provided() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/add-new-account-request-without-currency.json", CONTEXT))
                .when().post("/accounts")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void should_add_new_account_with_EUR_currency_when_currency_is_not_provided() throws IOException{
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/add-new-account-request-with-EUR-currency.json", CONTEXT))
                .when().post("/accounts")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    // edycja balansu
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

    // edycja waluty
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

    // usunięcie konta po id
    @Test
    void should_delete_GBP_account() {
        var id = "c7c6c077-931e-42f9-982a-8c836ab6b932";
        given().contentType(ContentType.JSON)
                .when().delete("/accounts/{%s}", id)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    // transfer
    @Test
    void should_transfer_50_from_EUR_account_to_USD_account() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/transfer.json", CONTEXT))
                .when().post("/transfers")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}