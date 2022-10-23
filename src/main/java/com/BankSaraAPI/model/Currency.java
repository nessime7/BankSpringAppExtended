package com.BankSaraAPI.model;

public enum Currency {
    // zamiast parametru, uzyc metody name() wszedzie, usunac parametr
    PLN("PLN"),
    CHF("CHF"),
    EUR("EUR"),
    USD("USD"),
    GBP("GBP");

    private String currencyName;

    Currency(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}

