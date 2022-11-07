package com.BankSaraAPI.repository;


import com.BankSaraAPI.model.Account;
import com.BankSaraAPI.model.Currency;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Repository
//public class BankRepository {
// 7
//    private List<Account> accounts = new ArrayList<>(List.of(
//            new Account(UUID.fromString("fc35ad28-91fc-449b-af3e-918417266f9d"), "main account", 10000.10, Currency.PLN),
//            new Account(UUID.fromString("5fd82e4e-c0ae-4771-a9d5-e18e3df32d65"), "savings account", 0, Currency.PLN),
//            new Account(UUID.fromString("5f73cec7-6ac1-46ee-a203-794c35d8800c"), "EUR account", 50, Currency.EUR),
//            new Account(UUID.fromString("3d9c2f62-d66c-4e31-89a7-ccdf271a7591"), "USD account", 50, Currency.USD),
//            new Account(UUID.fromString("e62ee6a0-4549-4956-a0dd-685f23526961"), "CHF account", 50, Currency.CHF),
//            new Account(UUID.fromString("7b93e505-2c0f-47a6-8ad6-6f6125c1c9a3"), "GBP account", 50, Currency.GBP),
//            new Account(UUID.fromString("c7c6c077-931e-42f9-982a-8c836ab6b932"), "GBP account", 250.5, Currency.PLN)));
//
//    public List<Account> getAccounts() {
//        return accounts;
//    }
//
//    public void save(Account account) {
//        accounts.add(account);
//    }
//
//    public void removeAccountById(UUID id) {
//        final var account = getAccounts().stream()
//                .filter(a -> a.getId().equals(id))
//                .findFirst()
//                .orElseThrow();
//        getAccounts().remove(account);
//
//    }
//
//    public void deleteAll() {
//        this.accounts = new ArrayList<>();
//    }
//
//    public Account getAccountById(UUID id) {
//        return getAccounts().stream()
//                .filter(a -> a.getId().equals(id))
//                .findFirst()
//                .orElseThrow();
//    }
//
//}
