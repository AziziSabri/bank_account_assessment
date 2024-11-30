package com.example.bank_account_assessment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bank_account_tbl")
@Inheritance(strategy = InheritanceType.JOINED)
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountHolder;
    private String accountHolderNormalized;
    private String accountNumber;
    private Long accountNumberNormalized;
    private LocalDateTime accountCreationDate;

    private double balance;

    public double availableBalance() {
        // must retain minimum balance of 50
        return balance - 50;
    }
}
