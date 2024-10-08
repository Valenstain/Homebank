package com.solodkov.homebank.mapper;

import com.solodkov.homebank.dto.BankAccountDto;
import com.solodkov.homebank.dto.DetailsBankAccountDto;
import com.solodkov.homebank.model.BankAccount;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankAccountMapper {

  @Mapping(target = "user.name", source = "username")
  @Mapping(target = "user.pin", source = "pin")
  @Mapping(target = "balance", source = "amount")
  BankAccount toBankAccount(BankAccountDto bankAccountDto);

  @Mapping(target = "username", source = "user.name")
  DetailsBankAccountDto toDetailsBankAccountDto(BankAccount bankAccount);

  @Mapping(target = "username", source = "user.name")
  List<DetailsBankAccountDto> toDetailsBankAccountDtoList(List<BankAccount> bankAccounts);
}
