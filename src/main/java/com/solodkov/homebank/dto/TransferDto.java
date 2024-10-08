package com.solodkov.homebank.dto;

import com.solodkov.homebank.enums.AccountOperation;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferDto(
    UUID fromAccountId,
    UUID toAccountId,
    @NotNull AccountOperation operation,
    @NotNull String pin,
    @NotNull BigDecimal amount
) {

}
