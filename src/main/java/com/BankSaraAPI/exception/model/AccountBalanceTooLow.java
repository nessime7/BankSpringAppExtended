package com.BankSaraAPI.exception.model;

public class AccountBalanceTooLow extends IllegalArgumentException {
    public AccountBalanceTooLow() {
        System.out.println("Account Balance Too Low");
    }
}
