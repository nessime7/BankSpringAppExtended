package com.BankSaraAPI.controller.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditAccountBalanceRequest {

    private double balance;

}
