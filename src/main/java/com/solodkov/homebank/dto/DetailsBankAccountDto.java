package com.solodkov.homebank.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record DetailsBankAccountDto(
    UUID accountId,
    String username,
    BigDecimal balance
) {

}
