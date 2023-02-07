package com.BankSpringAppExtended.card.repository;

import com.BankSpringAppExtended.card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
}
