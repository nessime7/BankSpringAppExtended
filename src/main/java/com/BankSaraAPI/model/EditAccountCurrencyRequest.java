package com.BankSaraAPI.model;

public class EditAccountCurrencyRequest {
    private Currency currency;

    public EditAccountCurrencyRequest(Currency currency) {
        this.currency = currency;
    }

    public EditAccountCurrencyRequest() {
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
