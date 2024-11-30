package com.example.bank_account_assessment.mappers;

import com.example.bank_account_assessment.dto.BankAccountDTO;
import com.example.bank_account_assessment.entities.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    BankAccountDTO entityToDTO(BankAccount bankAccount);
    BankAccount dtoToEntity(BankAccountDTO bankAccountDTO);
}
