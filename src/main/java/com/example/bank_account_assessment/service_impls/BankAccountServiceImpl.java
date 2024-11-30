package com.example.bank_account_assessment.service_impls;

import com.example.bank_account_assessment.dto.BankAccountDTO;
import com.example.bank_account_assessment.entities.BankAccount;
import com.example.bank_account_assessment.mappers.BaseMapper;
import com.example.bank_account_assessment.payload.request.BankAccountRequest;
import com.example.bank_account_assessment.repositories.BankAccountRepository;
import com.example.bank_account_assessment.service_interfaces.BankAccountServiceInterface;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl extends BaseMapper implements BankAccountServiceInterface {

    private final BankAccountRepository bankAccountRepository;

    @Override
    public BankAccountDTO createAccount(BankAccountRequest request) {

        BankAccount existingBankAccount = findByAccountNumber(request.getAccountNumber());

        if (existingBankAccount != null) {
            throw new EntityExistsException("Account already exists.");
        }

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolder(request.getAccountHolder());
        bankAccount.setAccountHolderNormalized(request.getAccountHolder().replaceAll("[^a-zA-Z0-9 ]", "").trim().toUpperCase());
        bankAccount.setAccountNumber(request.getAccountNumber());
        bankAccount.setAccountNumberNormalized(Long.valueOf(request.getAccountNumber().replace(" ", "").replace("-", "")));
        bankAccount.setBalance(request.getBalance());
        bankAccount.setAccountCreationDate(LocalDateTime.now());

        try {
            bankAccountRepository.save(bankAccount);
        } catch (Exception exception) {
            throw new RuntimeException("Account failed save: " + exception.getMessage());
        }


        return null;
    }

    @Override
    public void deposit(BankAccountRequest request) throws Exception {
        BankAccount bankAccount = findByAccountNumber(request.getAccountNumber());

        if (bankAccount == null) {
            throw new EntityNotFoundException("account not found: " + request.getAccountNumber());
        }

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Deposit amount must be more than 0");
        }

        double currentBalance = bankAccount.getBalance();
        bankAccount.setBalance(currentBalance + request.getAmount());

        try {
            bankAccountRepository.save(bankAccount);
        } catch (Exception exception) {
            throw new Exception("deposit cannot be performed: " + exception.getMessage());
        }

    }

    @Override
    public void withdraw(BankAccountRequest request) throws Exception {
        BankAccount bankAccount = findByAccountNumber(request.getAccountNumber());

        if (bankAccount == null) {
            throw new EntityNotFoundException("account not found: " + request.getAccountNumber());
        }

        double currentBalance = bankAccount.getBalance();
        if (bankAccount.availableBalance() >= request.getAmount()) {
            bankAccount.setBalance(currentBalance - request.getBalance());
            bankAccountRepository.save(bankAccount);
        }

        throw new IllegalArgumentException("Insufficient balance: withdrawal exceeds minimum account balance");
    }

    @Override
    public double getBalance(BankAccountRequest request) {
        BankAccount bankAccount = findByAccountNumber(request.getAccountNumber());

        if (bankAccount == null) {
            throw new EntityNotFoundException("account not found: " + request.getAccountNumber());
        }

        return bankAccount.getBalance();
    }

    private BankAccount findByAccountNumber(String accountNumber) {
        Long normalizedAccountNumber = Long.valueOf(accountNumber.replace(" ", "").replace("-", "")) ;

        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findByAccountNumberNormalized(normalizedAccountNumber);

        return bankAccountOptional.orElse(null);

    }
}
