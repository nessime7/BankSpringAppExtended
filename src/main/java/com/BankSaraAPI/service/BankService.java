package com.BankSaraAPI.service;

import com.BankSaraAPI.model.*;
import com.BankSaraAPI.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Account> getAccounts() {
        return bankRepository.getAccounts();
    }

    public void createAccount(CreateAccountRequest request) {
        if (request.getCurrency() == null) {
            request.setCurrency(Currency.PLN);
        }
        bankRepository.save(new Account(UUID.randomUUID(), request.getName(), request.getBalance(), request.getCurrency()));
    }

    public Account editAccount(UUID id, EditAccountBalance request) {
        return bankRepository.getAccounts()
                .stream().filter(a -> a.getId().equals(id))
                .peek(a -> a.setBalance(request.getBalance()))
                .findFirst()
                .orElseThrow();
    }

    public Account changeCurrency(UUID id, EditAccountCurrency request) {
        return bankRepository.getAccounts()
                .stream().filter(a -> a.getId().equals(id))
                .peek(a -> a.setCurrency(request.getCurrency()))
                .findFirst()
                .orElseThrow();
    }

    public void deleteAccount(UUID id) {
        bankRepository.removeAccountById(id);
    }

    public void transfer(AccountTransferRequest request) {
        final var senderAccount = bankRepository.getAccounts()
                .stream().filter(a -> a.getId().equals(request.getSenderId())).findFirst().orElseThrow();
        final var receiverAccount = bankRepository.getAccounts()
                .stream().filter(a -> a.getId().equals(request.getReceiverId())).findFirst().orElseThrow();
        senderAccount.setBalance(senderAccount.getBalance() - request.getAmount());
        receiverAccount.setBalance(receiverAccount.getBalance() + request.getAmount());
    }
}
