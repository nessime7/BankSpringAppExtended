package com.BankSaraAPI.model;

import java.util.UUID;

public class AccountTransferRequest {

    private UUID senderId;
    private UUID receiverId;
    private double amount;

    public AccountTransferRequest() {
    }

    public AccountTransferRequest(UUID senderId, UUID receiverId, double amount) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
