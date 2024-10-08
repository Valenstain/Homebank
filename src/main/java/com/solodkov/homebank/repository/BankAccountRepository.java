package com.solodkov.homebank.repository;

import com.solodkov.homebank.model.BankAccount;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

  Optional<BankAccount> findByAccountId(UUID accountId);
}
