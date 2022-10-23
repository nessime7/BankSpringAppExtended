package com.BankSaraAPI.exception.model;

import java.io.IOException;

public class TransferIsNotPossible extends IOException {
    public TransferIsNotPossible(){
        System.out.println("Transfer is not possible between this currencies");
    }
}
