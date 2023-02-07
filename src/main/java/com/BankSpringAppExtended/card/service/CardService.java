package com.BankSpringAppExtended.card.service;

import com.BankSpringAppExtended.card.dto.CreateCardRequest;
import com.BankSpringAppExtended.account.model.Account;
import com.BankSpringAppExtended.card.model.Card;
import com.BankSpringAppExtended.card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.BankSpringAppExtended.card.model.CardType.PAYMENT;

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
