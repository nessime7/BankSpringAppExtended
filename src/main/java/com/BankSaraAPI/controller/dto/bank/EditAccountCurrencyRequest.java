package com.BankSaraAPI.controller.dto.bank;

import com.BankSaraAPI.model.CurrencyType;

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
