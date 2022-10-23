package com.BankSaraAPI.service;

import com.BankSaraAPI.exception.model.AccountBalanceTooLow;
import com.BankSaraAPI.exception.model.TransferIsNotPossible;
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

    public void transferMethodWithoutConverter(AccountTransferRequest request) {
        final var senderAccount = bankRepository.getAccounts()
                .stream().filter(a -> a.getId().equals(request.getSenderId())).findFirst().orElseThrow();
        final var receiverAccount = bankRepository.getAccounts()
                .stream().filter(a -> a.getId().equals(request.getReceiverId())).findFirst().orElseThrow();
        senderAccount.setBalance(senderAccount.getBalance() - request.getAmount());
        receiverAccount.setBalance(receiverAccount.getBalance() + request.getAmount());
    }

    public void transfer(AccountTransferRequest request) throws TransferIsNotPossible {
        final var senderAccount = bankRepository.getAccounts()
                .stream().filter(a -> a.getId().equals(request.getSenderId())).findFirst().orElseThrow();
        final var receiverAccount = bankRepository.getAccounts()
                .stream().filter(a -> a.getId().equals(request.getReceiverId())).findFirst().orElseThrow();
        double sender = senderAccount.getBalance();
        double receiver = receiverAccount.getBalance();

        if (senderAccount.getBalance() < request.getAmount()){
            throw new AccountBalanceTooLow();
        }

        if (senderAccount.getBalance() >= request.getAmount()) {
            if (senderAccount.getCurrency().getCurrencyName().equals("PLN") && receiverAccount.getCurrency().getCurrencyName().equals("PLN")) {
                transferMethodWithoutConverter(request);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("EUR") && receiverAccount.getCurrency().getCurrencyName().equals("EUR")) {
                transferMethodWithoutConverter(request);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("USD") && receiverAccount.getCurrency().getCurrencyName().equals("USD")) {
                transferMethodWithoutConverter(request);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("CHF") && receiverAccount.getCurrency().getCurrencyName().equals("CHF")) {
                transferMethodWithoutConverter(request);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("GBP") && receiverAccount.getCurrency().getCurrencyName().equals("GBP")) {
                transferMethodWithoutConverter(request);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("USD") && receiverAccount.getCurrency().getCurrencyName().equals("EUR")) {
                transferMethodWithConverter(1.02, senderAccount, sender, request, receiverAccount, receiver);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("EUR") && receiverAccount.getCurrency().getCurrencyName().equals("USD")) {
                transferMethodWithConverter(0.98, senderAccount, sender, request, receiverAccount, receiver);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("USD") && receiverAccount.getCurrency().getCurrencyName().equals("PLN")) {
                transferMethodWithConverter(4.88, senderAccount, sender, request, receiverAccount, receiver);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("PLN") && receiverAccount.getCurrency().getCurrencyName().equals("USD")) {
                transferMethodWithConverter(0.20, senderAccount, sender, request, receiverAccount, receiver);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("EUR") && receiverAccount.getCurrency().getCurrencyName().equals("PLN")) {
                transferMethodWithConverter(4.77, senderAccount, sender, request, receiverAccount, receiver);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("PLN") && receiverAccount.getCurrency().getCurrencyName().equals("EUR")) {
                transferMethodWithConverter(0.21, senderAccount, sender, request, receiverAccount, receiver);
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("CHF") && receiverAccount.getCurrency().getCurrencyName().equals("PLN")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("PLN") && receiverAccount.getCurrency().getCurrencyName().equals("CHF")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("CHF") && receiverAccount.getCurrency().getCurrencyName().equals("EUR")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("EUR") && receiverAccount.getCurrency().getCurrencyName().equals("CHF")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("CHF") && receiverAccount.getCurrency().getCurrencyName().equals("USD")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("USD") && receiverAccount.getCurrency().getCurrencyName().equals("CHF")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("CHF") && receiverAccount.getCurrency().getCurrencyName().equals("GBP")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("GBP") && receiverAccount.getCurrency().getCurrencyName().equals("CHF")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("GBP") && receiverAccount.getCurrency().getCurrencyName().equals("PLN")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("PLN") && receiverAccount.getCurrency().getCurrencyName().equals("GBP")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("EUR") && receiverAccount.getCurrency().getCurrencyName().equals("GBP")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("GBP") && receiverAccount.getCurrency().getCurrencyName().equals("EUR")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("GBP") && receiverAccount.getCurrency().getCurrencyName().equals("USD")) {
                throw new TransferIsNotPossible();
            }
            if (senderAccount.getCurrency().getCurrencyName().equals("USD") && receiverAccount.getCurrency().getCurrencyName().equals("GBP")) {
                throw new TransferIsNotPossible();
            }
        }
    }

    private void transferMethodWithConverter(double converter, Account senderAccount, double sender, AccountTransferRequest request, Account receiverAccount, double receiver) {
        senderAccount.setBalance(sender - (request.getAmount()));
        receiverAccount.setBalance((receiver * converter) + ((request.getAmount()) * converter));
    }

}