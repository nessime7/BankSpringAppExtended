package com.BankSaraAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
/*
1. Dodajemy encje karty, musi ona posiadac, id, imie i nazwisko, limit gorny, maksymalny debet,
typ(kredytowa- mozna zaciagac kredyt(debet), platnicza- nie mozna zaciagac debetu).
2. konto moze miec wiele kart, ale moze nie miec zadnej
3. Dodac endpoint dodajacy karte dla konkretnego uzytkownika i napisac testy
3. Dodac endpoint usuwajace karte dla konkretnego uzytkownika i napisac testy
4. Endpoint accounts zwraca uzytkownikow wraz z ich kartami, jesli jakies posiadaja, dostosowac testy
 */
public class BankSaraApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSaraApplication.class, args);
	}

}