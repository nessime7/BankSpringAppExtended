package com.BankSaraAPI.service;

import com.BankSaraAPI.controller.dto.card.CreateCardRequest;
import com.BankSaraAPI.model.Account;
import com.BankSaraAPI.model.Card;
import com.BankSaraAPI.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.BankSaraAPI.model.CardType.PAYMENT;

@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void addCardToAccount(Account account, CreateCardRequest createCardRequest) {
        final var card = new Card(
                account.getId(),
                createCardRequest.getName(),
                createCardRequest.getSurname(),
                createCardRequest.getMaxLimit(),
                createCardRequest.getMaxDebet(),
                createCardRequest.getCardType());
        if (createCardRequest.getCardType().equals(PAYMENT)) {
            card.setMaxDebet(0);
        }
        cardRepository.save(card);
    }

    public void deleteCard(UUID cardId) {
        cardRepository.deleteById(cardId);
    }

}
