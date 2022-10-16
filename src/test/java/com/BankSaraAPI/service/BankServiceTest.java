package com.BankSaraAPI.service;

import com.BankSaraAPI.model.*;
import com.BankSaraAPI.repository.BankRepository;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BankServiceTest {
    private final BankRepository bankRepository = mock(BankRepository.class);
    private final BankService bankService = new BankService(bankRepository);

    @Test
    void should_get_all_accounts() {
        // given
        when(bankRepository.getAccounts()).thenReturn(Set.of(new Account(UUID.randomUUID(), "Glowne", 1, Currency.PLN)));
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
        when(bankRepository.getAccounts()).thenReturn(Set.of(account));
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
        when(bankRepository.getAccounts()).thenReturn(Set.of(account));
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
        when(bankRepository.getAccounts()).thenReturn(Set.of(account));
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
        var accountRequest = new EditAccountBalance(2);
        var account = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477"), "Glowne", 1, Currency.PLN);
        when(bankRepository.getAccounts()).thenReturn(Set.of(account));
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
    void should_trasnfer() {
        // given
        var sender = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477"), "Glowne", 100, Currency.PLN);
        var receiver = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95471"), "Glowne", 0, Currency.PLN);
        when(bankRepository.getAccounts()).thenReturn(Set.of(sender, receiver));
        var transfer = new AccountTransferRequest(sender.getId(), receiver.getId(), 100);
        // when
        bankService.transfer(transfer);
        // then
        assertEquals(100, receiver.getBalance());
    }

    @Test
    void should_change_currency_from_PLN_to_EUR() {
        // given
        var id = UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477");
        var accountRequest = new EditAccountCurrency(Currency.EUR);
        var account = new Account(UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477"), "Glowne", 1, Currency.PLN);
        when(bankRepository.getAccounts()).thenReturn(Set.of(account));
        // when
        bankService.changeCurrency(id, accountRequest);
        // then
        assertEquals(Currency.EUR, account.getCurrency());
    }
}