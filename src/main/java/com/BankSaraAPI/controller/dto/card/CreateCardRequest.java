package com.BankSaraAPI.controller.dto.card;

import com.BankSaraAPI.model.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

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
