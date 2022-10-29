package com.BankSaraAPI.config;

public class Checks {

    public static void checkThat(boolean condition, String errorMessage) {
        if (!condition)
            throw new IllegalArgumentException(errorMessage);
    }

    private Checks() {
        throw new AssertionError("No Checks instances for you!");
    }
}
