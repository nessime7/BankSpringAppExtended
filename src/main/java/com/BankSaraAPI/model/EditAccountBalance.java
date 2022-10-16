package com.BankSaraAPI.model;

public class EditAccountBalance {

    private double balance;

    public EditAccountBalance() {
    }

    public EditAccountBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
