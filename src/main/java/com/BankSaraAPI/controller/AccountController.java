package com.BankSaraAPI.controller;

import com.BankSaraAPI.controller.dto.bank.AccountTransferRequest;
import com.BankSaraAPI.controller.dto.bank.CreateAccountRequest;
import com.BankSaraAPI.controller.dto.bank.EditAccountBalanceRequest;
import com.BankSaraAPI.controller.dto.bank.EditAccountCurrencyRequest;
import com.BankSaraAPI.model.*;
import com.BankSaraAPI.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/*
- account management
    - get/create/edit/delete account
    - transfer between accounts
 GET/POST/PUT/DELETE
 */

@RestController
public class AccountController {

    private final AccountService accountService;

    // dependency injection
    // jak będzie widział tą klasę to musi stworzyć sam tą klasę
    // gdzieś tam pod spodem, konstrukt taki, który nam stworzy tą klasę
    // BankController z accountService
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // wyświetlenie wszystkich kont
    @GetMapping("accounts")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    // stworzenie nowego konta
    @PostMapping("accounts")
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountRequest request) {
        accountService.createAccount(request);
        return ResponseEntity.noContent().build();
    }

    // edit balance
    @PutMapping("accounts/{id}/balance")
    public ResponseEntity<Account> editAccount(@PathVariable UUID id, @RequestBody EditAccountBalanceRequest request) {
        final var account = accountService.editAccount(id, request);
        return ResponseEntity.ok(account);
    }

    // edit currency
    @PutMapping("accounts/{id}/currency")
    public ResponseEntity<Account> editCurrency(@PathVariable UUID id, @RequestBody EditAccountCurrencyRequest request) {
        final var account = accountService.changeCurrency(id, request);
        return ResponseEntity.ok(account);
    }

    // delete id
    @DeleteMapping("accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    // transfer
    @PostMapping("transfers")
    public ResponseEntity<Void> editAccount(@RequestBody AccountTransferRequest request) throws SQLException {
        accountService.transfer(request);
        return ResponseEntity.noContent().build();
    }

}
