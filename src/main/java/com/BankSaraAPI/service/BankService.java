package com.BankSaraAPI.service;

import com.BankSaraAPI.common.MenuManagerExceptionMessages;
import com.BankSaraAPI.db.DataBaseRepository;
import com.BankSaraAPI.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.EntityManager;

import static com.BankSaraAPI.model.Currency.*;

@Service
public class BankService {

    private final DataBaseRepository dataBaseRepository;

    @Autowired
    public BankService(DataBaseRepository dataBaseRepository) {
        this.dataBaseRepository = dataBaseRepository;
    }

    public Account findAccount(UUID accountID) {
        return dataBaseRepository.findById(accountID)
                .orElseThrow(() -> new IllegalStateException(MenuManagerExceptionMessages.ACCOUNT_NOT_FOUND));
    }

    public List<Account> getAccounts() {
        final List<Account> accountsPosition = dataBaseRepository.findAll();
        return accountsPosition;
    }

    public void createAccount(CreateAccountRequest request) {
        if (request.getCurrency() == null) {
            request.setCurrency(Currency.PLN);
        }
        dataBaseRepository.save(new Account(UUID.randomUUID(), request.getName(), request.getBalance(), request.getCurrency()));
    }

    public Account editAccount(UUID id, EditAccountBalanceRequest request) {
        final var accountPosition = findAccount(id);
        accountPosition.setBalance(request.getBalance());
        return dataBaseRepository.save(accountPosition);
    }

    public Account changeCurrency(UUID id, EditAccountCurrencyRequest request) {
        final var accountPosition = findAccount(id);
        accountPosition.setCurrency(request.getCurrency());
        return dataBaseRepository.save(accountPosition);
    }

    public void deleteAccount(UUID id) {
        final var accountToDelete = findAccount(id);
        dataBaseRepository.delete(accountToDelete);
    }

    Set<Currency> forbiddenCurrencies = Set.of(CHF, GBP);

    public void transfer(AccountTransferRequest request) throws SQLException {
        Account sender = dataBaseRepository.getById(request.getSenderId());
        double senderBalance = sender.getBalance();
        Account receiver = dataBaseRepository.getById(request.getReceiverId());
        double receiverBalance = receiver.getBalance();

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException(MenuManagerExceptionMessages.CANNOT_BE_LESS_THAN_ZERO);
        }

        if (senderBalance < request.getAmount()) {
            throw new IllegalStateException(MenuManagerExceptionMessages.ACCOUNT_BALANCE_TOO_LOW);
        }

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
            multiCurrencyTransfer(sender, request, receiver, converter);
        }
        if (sender.getCurrency().equals(EUR) && receiver.getCurrency().equals(USD)) {
            double converter = 0.98;
            multiCurrencyTransfer(sender, request, receiver, converter);
        }
        if (sender.getCurrency().equals(USD) && receiver.getCurrency().equals(PLN)) {
            double converter = 4.88;
            multiCurrencyTransfer(sender, request, receiver, converter);
        }
        if (sender.getCurrency().equals(PLN) && receiver.getCurrency().equals(USD)) {
            double converter = 0.20;
            multiCurrencyTransfer(sender, request, receiver, converter);
        }
        if (sender.getCurrency().equals(EUR) && receiver.getCurrency().equals(PLN)) {
            double converter = 4.77;
            multiCurrencyTransfer(sender, request, receiver, converter);
        }
        if (sender.getCurrency().equals(PLN) && receiver.getCurrency().equals(EUR)) {
            double converter = 0.21;
            multiCurrencyTransfer(sender, request, receiver, converter);
        }

    }

    private void multiCurrencyTransfer(Account sender,
                                       AccountTransferRequest request,
                                       Account receiver,
                                       double converter) {
        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + (request.getAmount()) * converter);
        dataBaseRepository.save(sender);
        dataBaseRepository.save(receiver);
    }

    public void transferWithoutConverter(AccountTransferRequest request, Account sender, Account receiver) {
        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());
        dataBaseRepository.save(sender);
        dataBaseRepository.save(receiver);


    }

}

