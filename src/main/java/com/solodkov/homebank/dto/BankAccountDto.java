package com.solodkov.homebank.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BankAccountDto(
    @NotNull String username,
    @NotNull String pin,
    @NotNull BigDecimal amount
) {

}
