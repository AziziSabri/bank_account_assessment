package com.example.bank_account_assessment.payload.request;

import com.example.bank_account_assessment.utils.Constants;
import lombok.Data;

@Data
public class BankAccountRequest {
    private String accountHolder;
    private String accountNumber;
    private Constants.TransactionType transactionType;
    private double balance;
    private double amount;
}
