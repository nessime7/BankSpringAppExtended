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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

// adnotacja używana do rejestrowania rozszerzeń dla klasy testowej
@ExtendWith(SpringExtension.class) // klasa która implementuje @BeforeEach
// uruchamia testy oparte na Spring Boot dla klasy BankSaraApplication
// random port - testujemy z prawdziwym serwerem
@SpringBootTest(classes = {BankSaraApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankIntegrationTest {

// wstrzykuje port http, port -  proces przeniesienia wersji programu komputerowego
// na inną platformę sprzętową bądź programistyczną,
// zazwyczaj na inną architekturę procesora lub system operacyjny
    @LocalServerPort
    private int port;
    // kontekst aktualnego stanu aplikacji/obiektu,
    // zdefiniowanie stringa aby nie powtarzać za każdym razem w ścieżce
    private static final String CONTEXT = "account";

    @BeforeEach
    void setUp() {
        // biblioteka Java, która zapewnia język specyficzny dla domeny (DSL) do pisania zaawansowanych,
        // łatwych do utrzymania testów dla interfejsów API zgodnych z REST.
        // udostępnia nam całą składnię
        RestAssured.port = port;
        // włącz rejestrowanie żądania i odpowiedzi
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    // wyświetlanie wszystkich kont
    @Test
    void should_return_all_accounts() {
        given()
                .when().get("/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK);
        // content should be checked
    }

    // stworzenie nowego konta
    @Test
    void should_add_new_account() throws IOException {
        given().contentType(ContentType.JSON)
                .body(TestUtils.getRequestBodyFromFile("request/add-new-account-request.json", CONTEXT))
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
