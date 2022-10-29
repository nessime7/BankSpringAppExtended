package com.BankSaraAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*
- poczytanie o wyjątkach i poprawienie ich w projekcie
- parsowanie wyjątków DONE
- prosze poczytac na internacie o bibliotece swagger i dodac ja do projektu DONE
*/

@EnableWebMvc
@SpringBootApplication
public class BankSaraApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSaraApplication.class, args);
	}

}