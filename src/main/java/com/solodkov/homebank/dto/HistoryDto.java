package com.solodkov.homebank.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record HistoryDto(
        UUID accountId,
        String username,
        String operation,
        BigDecimal amount,
        String currency
) {
}
