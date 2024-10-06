package com.solodkov.homebank.controller;

import com.solodkov.homebank.dto.HistoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Tag(name = "History Controller",
description = "Этот контроллер предназначен для работы с иторией транзакций")
public interface HistoryController {

    @Operation(
            summary = "Получение истории транзакций"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "История транзакций успешно загружена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HistoryDto.class))
            )
    })
    @GetMapping("/history/{accountId}")
    ResponseEntity<List<HistoryDto>> getHistory(
            @Parameter(name = "accountId",
                    description = "Идентификационный номер счета")
            UUID accountId,
            @Parameter(name = "pin",
                    description = "Пин код для осуществления операции")
            String pin);
}
