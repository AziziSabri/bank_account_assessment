package com.example.bank_account_assessment.dto;

import lombok.Data;

@Data
public class BankAccountDTO {
    private Long id;
    private String accountHolder;
    private double balance;
}
