package com.example.bank_account_assessment.service_interfaces;

import com.example.bank_account_assessment.dto.BankAccountDTO;
import com.example.bank_account_assessment.payload.request.BankAccountRequest;

public interface BankAccountServiceInterface {
    BankAccountDTO createAccount(BankAccountRequest request);
    void deposit(BankAccountRequest request) throws Exception;
    void withdraw(BankAccountRequest request) throws Exception;
    double getBalance(BankAccountRequest request);
}
