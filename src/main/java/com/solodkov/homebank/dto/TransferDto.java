package com.solodkov.homebank.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferDto(
        @NotNull UUID accounId,
        @NotNull String pin,
        @NotNull BigDecimal amount
) {
}
