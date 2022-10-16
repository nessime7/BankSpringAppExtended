package com.BankSaraAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 prosze wprowadzic do aplikacji waluty dla kont
- uzytkownik powinien miec mozliwosc tworzenia konta z okreslona waluta,
ale nie jest to wymagane, jesli nie jest podana waluta, domyslnie przypisujemy PLN
- uzytkownik powinien miec mozliwosc zmiany waluty konta
- reszta funkcjonalnosci ma dzialac tak jak dzialala
*/

@SpringBootApplication
public class BankSaraApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSaraApplication.class, args);
	}

}
