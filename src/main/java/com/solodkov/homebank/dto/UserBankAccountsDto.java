package com.solodkov.homebank.dto;

import java.util.List;

public record UserBankAccountsDto(
    String username,
    List<DetailsBankAccountDto> bankAccounts
) {

}
