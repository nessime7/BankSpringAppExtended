package com.BankSaraAPI.service;

import com.BankSaraAPI.exception.model.AccountBalanceTooLow;
import com.BankSaraAPI.exception.model.TransferIsNotPossible;
import com.BankSaraAPI.model.*;
import com.BankSaraAPI.repository.BankRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BankServiceTest {
    private final BankRepository bankRepository = mock(BankRepository.class);
    private final BankService bankService = new BankService(bankRepository);

    @Test
    void should_get_all_accounts() {
        // given
        when(bankRepository.getAccounts()).thenReturn(List.of(new Account(UUID.randomUUID(), "Glowne", 1, Currency.PLN)));
        // when
        var accounts = bankService.getAccounts().size();
        // then
        assertEquals(1, accounts);
    }

    @Test
    void should_create_account() {
        // given
        var accountRequest = new CreateAccountRequest("Glowne", 1, Currency.PLN);
        var account = new Account(UUID.randomUUID(), "Glowne", 1, Currency.PLN);
        when(bankRepository.getAccounts()).thenReturn(List.of(account));
        // when
        bankService.createAccount(accountRequest);
        // then
        assertEquals("Glowne", accountRequest.getName());
        assertEquals(Currency.PLN, accountRequest.getCurrency());
    }

    @Test
    void should_create_account2() {
        // given
        var accountRequest = new CreateAccountRequest("Glowne", 1, Currency.EUR);
        var account = new Account(UUID.randomUUID(), "Glowne", 1, Currency.EUR);
        when(bankRepository.getAccounts()).thenReturn(List.of(account));
        // when
        bankService.createAccount(accountRequest);
        // then
        assertEquals("Glowne", accountRequest.getName());
        assertEquals(Currency.EUR, accountRequest.getCurrency());
    }

    @Test
    void should_create_account_with_PLN_when_currency_is_null() {
        // given
        var accountRequest = new CreateAccountRequest("Glowne", 1, null);
        var account = new Account(UUID.randomUUID(), "Glowne", 1, null);
        when(bankRepository.getAccounts()).thenReturn(List.of(account));
        // when
        bankService.createAccount(accountRequest);
        // then
        assertEquals("Glowne", accountRequest.getName());
        assertEquals(Currency.PLN, accountRequest.getCurrency());
    }

    @Test
    void should_edit_account() {
        // given
        var id = UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477");
        var accountRequest = new EditAccountBalanceRequest(2);
        var account = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477"), "Glowne", 1, Currency.PLN);
        when(bankRepository.getAccounts()).thenReturn(List.of(account));
        // when
        var editStudent = bankService.editAccount(id, accountRequest);
        // then
        assertEquals(2, editStudent.getBalance());
    }

    @Test
    void should_delete_account() {
        // given
        var id = UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477");
        // when
        bankService.deleteAccount(id);
        // then
        then(bankRepository).should().removeAccountById(id);
    }

    @Test
    void should_transfer_when_sender_amount_is_bigger_than_transfer_amount() throws TransferIsNotPossible {
        // given
        var sender = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477"), "Glowne", 100, Currency.PLN);
        var receiver = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95471"), "Glowne", 0, Currency.PLN);
        when(bankRepository.getAccounts()).thenReturn(List.of(sender, receiver));
        var transfer = new AccountTransferRequest(sender.getId(), receiver.getId(), 100);
        // when
        bankService.transfer(transfer);
        // then
        assertEquals(100, receiver.getBalance());
    }

    @Test
    void should_not_transfer_when_sender_amount_is_lower_than_transfer_amount() {
        // given
        var sender = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477"), "Glowne", 50, Currency.PLN);
        var receiver = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95471"), "Glowne", 0, Currency.PLN);
        when(bankRepository.getAccounts()).thenReturn(List.of(sender, receiver));
        var transfer = new AccountTransferRequest(sender.getId(), receiver.getId(), 100);

        // then
        assertThrows(AccountBalanceTooLow.class, () -> bankService.transfer(transfer));
        assertEquals(0, receiver.getBalance());
        assertEquals(50, sender.getBalance());
    }

    @Test
    void should_not_transfer_when_sender_is_PLN_and_receiver_is_GBP(){
        // given
        var sender = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477"), "Glowne", 50, Currency.PLN);
        var receiver = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95471"), "Glowne", 0, Currency.GBP);
        when(bankRepository.getAccounts()).thenReturn(List.of(sender, receiver));
        var transfer = new AccountTransferRequest(sender.getId(), receiver.getId(), 1);

        // then
        assertThrows(TransferIsNotPossible.class, () -> bankService.transfer(transfer));
        assertEquals(0, receiver.getBalance());
        assertEquals(50, sender.getBalance());
    }

    @Test
    void should_change_currency_from_PLN_to_EUR() {
        // given
        var id = UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477");
        var accountRequest = new EditAccountCurrencyRequest(Currency.EUR);
        var account = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477"), "Glowne", 1, Currency.PLN);
        when(bankRepository.getAccounts()).thenReturn(List.of(account));

        // when
        bankService.changeCurrency(id, accountRequest);

        // then
        assertEquals(Currency.EUR, account.getCurrency());
    }

    @Test
    void should_transfer_USD_to_EUR() throws TransferIsNotPossible {
        // given
        var sender = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477"), "USD account", 1, Currency.USD);
        var receiver = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95471"), "EUR account", 0, Currency.EUR);
        when(bankRepository.getAccounts()).thenReturn(List.of(sender, receiver));
        var transfer = new AccountTransferRequest(sender.getId(), receiver.getId(), 1);

        // when
        bankService.transfer(transfer);

        // then
        // sprawdz czy zostalo wywolane bankRepository.getAccounts()
        assertEquals(0, sender.getBalance());
        assertEquals(1.02, receiver.getBalance());
    }
}