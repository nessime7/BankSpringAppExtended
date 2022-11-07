package com.BankSaraAPI.db;

import com.BankSaraAPI.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DataBaseRepository extends JpaRepository<Account, UUID> {
    Account findAllById(UUID id);
}
