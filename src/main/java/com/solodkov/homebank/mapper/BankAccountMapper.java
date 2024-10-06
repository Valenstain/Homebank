package com.solodkov.homebank.mapper;

import com.solodkov.homebank.dto.BankAccountDto;
import com.solodkov.homebank.model.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankAccountMapper {

    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "pin", source = "pin")
    @Mapping(target = "balance", source = "amount")
    BankAccount toBankAccount(BankAccountDto bankAccountDto);

}
