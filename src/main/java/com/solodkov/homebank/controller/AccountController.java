package com.solodkov.homebank.controller;

import com.solodkov.homebank.dto.BankAccountDto;
import com.solodkov.homebank.dto.DetailsBankAccountDto;
import com.solodkov.homebank.dto.TransferDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Tag(name = "Account Controller",
        description = "Используется для работы со счетами")
public interface AccountController {

    @Operation(
            summary = "Получение списка всех счетов закрепленных за пользователем"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получение списка счетов выполнено успешно",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DetailsBankAccountDto.class)
                            )
                    )
            }
    )
    @GetMapping("/details/all/{username}")
    ResponseEntity<List<DetailsBankAccountDto>> getBankAccounts(
            @Parameter(
                    name = "username",
                    description = "Имя пользователя")
            String username,
            @Parameter(
                    name = "pin",
                    description = "Пин код для осуществления операции")
            String pin
    );

    @Operation(
            summary = "Получение детальной информации о счете"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получение детальной информации о счете выполнено успешно",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DetailsBankAccountDto.class)
                            )
                    )
            }
    )
    @GetMapping("/details/{accountId}")
    ResponseEntity<DetailsBankAccountDto> detailsAccount(
            @Parameter(name = "accountId",
                    description = "Идентификационный номер счета")
            UUID accountId,
            @Parameter(name = "pin",
                    description = "Пин код для осуществления операции")
            String pin);

    @Operation(
            summary = "Создание нового счета"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Счет успешно добавлен",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DetailsBankAccountDto.class)
                            )
                    )
            }
    )
    @PostMapping("/add")
    ResponseEntity<DetailsBankAccountDto> addAccount(
            @Parameter(name = "bankAccountDto",
                    description = "Для создания счета укажите имя пользователя (username), " +
                            "пин код (pin), который необходим для соверщения операций, начальное кол-во " +
                            "активов на счету (amount), а таже валюту. На данный момент система " +
                            "поддерживает только 2 валюты (usd,rub). Будьте внимательны, в противном " +
                            "случае операция не будет выполнена.",
                    schema = @Schema(implementation = BankAccountDto.class))
            BankAccountDto bankAccountDto,
            @Parameter(name = "pin",
                    description = "Пин код для осуществления операции")
            String pin);

    @Operation(
            summary = "Операции со счетами: переводы, вывод, добавление"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Операция успешно выполнена",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DetailsBankAccountDto.class)
                            )
                    )
            }
    )
    @PatchMapping("/transfer")
    ResponseEntity<DetailsBankAccountDto> transfer(
            @Parameter(name = "transferDto",
                    description = "Данные для переводов, вывода, добавления. Для перевода необхоидмо " +
                            "выбрать TRANSFER и указать счет откуда (fromAccountId) и куда (toAccountId) " +
                            "будет осуществлен перевод, для вывода необходимо выбрать WITHDRAW и указать " +
                            "номер счета вывода (fromAccountId), для добавления средств необходимо выбрать " +
                            "ADD и указать счет для зачисления (toAccountId). Для успешной операции необходимо " +
                            "также указать PIN и количество",
                    schema = @Schema(implementation = TransferDto.class))
            TransferDto transferDto,
            @Parameter(name = "pin",
                    description = "Пин код для осуществления операции")
            String pin);
}
