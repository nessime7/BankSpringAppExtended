package com.BankSaraAPI.exception.model;

public class CannotBeLessThanZero extends IllegalArgumentException{
    public CannotBeLessThanZero(){
        System.out.println("The transfer amount cannot be less than 0!");
    }
}
