package com.BankSaraAPI.model;

public class EditAccountCurrency {
    private Currency currency;

    public EditAccountCurrency(Currency currency) {
        this.currency = currency;
    }

    public EditAccountCurrency() {
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
