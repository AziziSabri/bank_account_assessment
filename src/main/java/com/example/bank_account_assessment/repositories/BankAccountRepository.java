package com.example.bank_account_assessment.repositories;

import com.example.bank_account_assessment.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByAccountNumberNormalized(Long accountNumber);
    Optional<BankAccount> findByAccountHolderNormalized(String accountHolder);
}
