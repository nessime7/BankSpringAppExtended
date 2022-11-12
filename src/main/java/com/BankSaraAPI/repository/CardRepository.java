package com.BankSaraAPI.repository;

import com.BankSaraAPI.model.Account;
import com.BankSaraAPI.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
}
