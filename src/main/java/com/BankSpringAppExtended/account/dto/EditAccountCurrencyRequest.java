package com.BankSpringAppExtended.account.dto;

import com.BankSpringAppExtended.account.model.CurrencyType;

public class EditAccountCurrencyRequest {
    private CurrencyType currency;

    public EditAccountCurrencyRequest(CurrencyType currency) {
        this.currency = currency;
    }

    public EditAccountCurrencyRequest() {
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }
}
