package com.BankSpringAppExtended;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class BankSpringAppExtended {

	public static void main(String[] args) {
		SpringApplication.run(BankSpringAppExtended.class, args);
	}

}