package com.BankSaraAPI.model;

public class EditAccountBalanceRequest {

    private double balance;

    public EditAccountBalanceRequest() {
    }

    public EditAccountBalanceRequest(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
