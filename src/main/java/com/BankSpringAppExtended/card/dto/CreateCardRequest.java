package com.BankSpringAppExtended.card.dto;

import com.BankSpringAppExtended.card.model.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardRequest {
//    private UUID accountId;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    private double maxLimit;
    private double maxDebet;
    private CardType cardType;

}
