package com.BankSaraAPI.service;

import com.BankSaraAPI.config.Checks;
import com.BankSaraAPI.exception.model.MenuManagerExceptionMessages;
import com.BankSaraAPI.model.*;
import com.BankSaraAPI.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.BankSaraAPI.model.Currency.*;

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

    public Account editAccount(UUID id, EditAccountBalanceRequest request) {
        return bankRepository.getAccounts()
                .stream().filter(a -> a.getId().equals(id))
                .peek(a -> a.setBalance(request.getBalance()))
                .findFirst()
                .orElseThrow();
    }

    public Account changeCurrency(UUID id, EditAccountCurrencyRequest request) {
        return bankRepository.getAccounts()
                .stream().filter(a -> a.getId().equals(id))
                .peek(a -> a.setCurrency(request.getCurrency()))
                .findFirst()
                .orElseThrow();
    }

    public void deleteAccount(UUID id) {
        bankRepository.removeAccountById(id);
    }

    // czyzm sie rozni checked od unchecked exception, ma byc unchecked

    Set<Currency> forbiddenCurrencies = Set.of(CHF, GBP);

    public void transfer(AccountTransferRequest request) {
        Account sender = bankRepository.getAccountById(request.getSenderId());
        double senderBalance = sender.getBalance();
        Account receiver = bankRepository.getAccountById(request.getReceiverId());
        double receiverBalance = receiver.getBalance();

//        Checks.checkThat((request.getAmount() <=0), MenuManagerExceptionMessages.CANNOT_BE_LESS_THAN_ZERO);
        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException(MenuManagerExceptionMessages.CANNOT_BE_LESS_THAN_ZERO);
        }

//        Checks.checkThat(senderBalance < request.getAmount(),MenuManagerExceptionMessages.ACCOUNT_BALANCE_TOO_LOW);

        if (senderBalance < request.getAmount()) {
            throw new IllegalStateException(MenuManagerExceptionMessages.ACCOUNT_BALANCE_TOO_LOW);
        }

//        Checks.checkThat(((forbiddenCurrencies.contains(sender.getCurrency()) ||
//                forbiddenCurrencies.contains(receiver.getCurrency()))
//                && !sender.getCurrency().equals(receiver.getCurrency())),MenuManagerExceptionMessages.TRANSFER_IS_NOT_POSSIBLE);

        if ((forbiddenCurrencies.contains(sender.getCurrency()) ||
                forbiddenCurrencies.contains(receiver.getCurrency()))
                && !sender.getCurrency().equals(receiver.getCurrency())) {
            throw new IllegalArgumentException(MenuManagerExceptionMessages.TRANSFER_IS_NOT_POSSIBLE);
        }

        if (sender.getCurrency().equals(receiver.getCurrency())) {
            transferWithoutConverter(request, sender, receiver);
        }
        if (sender.getCurrency().equals(USD) && receiver.getCurrency().equals(EUR)) {
            double converter = 1.02;
            multiCurrencyTranfer(sender, request, receiver, converter);
        }
        if (sender.getCurrency().equals(EUR) && receiver.getCurrency().equals(USD)) {
            double converter = 0.98;
            multiCurrencyTranfer(sender, request, receiver, converter);
        }
        if (sender.getCurrency().equals(USD) && receiver.getCurrency().equals(PLN)) {
            double converter = 4.88;
            multiCurrencyTranfer(sender, request, receiver, converter);
        }
        if (sender.getCurrency().equals(PLN) && receiver.getCurrency().equals(USD)) {
            double converter = 0.20;
            multiCurrencyTranfer(sender, request, receiver, converter);
        }
        if (sender.getCurrency().equals(EUR) && receiver.getCurrency().equals(PLN)) {
            double converter = 4.77;
            multiCurrencyTranfer(sender, request, receiver, converter);
        }
        if (sender.getCurrency().equals(PLN) && receiver.getCurrency().equals(EUR)) {
            double converter = 0.21;
            multiCurrencyTranfer(sender, request, receiver, converter);
        }
    }

    private void multiCurrencyTranfer(Account sender,
                                      AccountTransferRequest request,
                                      Account receiver,
                                      double converter) {
        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + (request.getAmount()) * converter);
    }

    public void transferWithoutConverter(AccountTransferRequest request, Account sender, Account receiver) {
        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());
    }
}