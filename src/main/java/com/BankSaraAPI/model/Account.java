package com.BankSaraAPI.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.UUID;

// obiekt danej klasy reprezentuje rekord w tabeli w bazie danych
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public final class Account {

    @Id
    @GeneratedValue(generator = "id")
    private UUID id;
    @NotBlank
    private String name;

    @Positive
    private double balance;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    // new
    public Account(String name, double balance, Currency currency) {
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public double getBalance() {
//        return balance;
//    }
//
//    public void setBalance(double balance) {
//        this.balance = balance;
//    }
//
//    public Currency getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(Currency currency) {
//        this.currency = currency;
//    }
}
