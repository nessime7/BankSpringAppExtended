package com.BankSaraAPI.repository;


import com.BankSaraAPI.model.Account;
import com.BankSaraAPI.model.Currency;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public class BankRepository {

    private Set<Account> accounts = new HashSet<>(Set.of(
            new Account(UUID.randomUUID(), "main account", 10000.10, Currency.PLN),
            new Account(UUID.randomUUID(), "savings account", 0, Currency.PLN),
            new Account(UUID.randomUUID(), "GBP account", 250.5,Currency.PLN)));

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void save(Account account) {
        accounts.add(account);
    }

    public void removeAccountById(UUID id) {
        final var account = getAccounts().stream()
                .filter(a->a.getId().equals(id))
                .findFirst()
                .orElseThrow();
        getAccounts().remove(account);

    }
}
