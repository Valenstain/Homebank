package com.solodkov.homebank.controller.impl;

import com.solodkov.homebank.controller.AccountController;
import com.solodkov.homebank.dto.BankAccountDto;
import com.solodkov.homebank.dto.DetailsBankAccountDto;
import com.solodkov.homebank.dto.TransferDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Контроллер по работе со счетами
 */
@Validated
@RestController("/homebank/account")
public class AccountControllerImpl implements AccountController {

    /**
     * "Получение списка всех счетов
     * закрепленных за пользователем
     *
     * @param username имя пользователя
     * @param pin      пин код
     * @return ответ в виде JSON
     */
    @Override
    @GetMapping("/details/all/{username}")
    public ResponseEntity<List<DetailsBankAccountDto>> getBankAccounts(
            @PathVariable("username") String username,
            @RequestParam("pin") String pin) {

        List<DetailsBankAccountDto> bankAccounts = new ArrayList<>();

        DetailsBankAccountDto accountDto = new DetailsBankAccountDto(
                UUID.randomUUID(),
                "Solodkov",
                BigDecimal.valueOf(0.0),
                "usd"
        );

        bankAccounts.add(accountDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bankAccounts);
    }

    /**
     * По запросу предоставляет подробную
     * информацию о счете
     *
     * @param accountId идентификатор счета
     * @param pin       пин код
     * @return возвращает ответ в виде JSON
     */
    @Override
    @GetMapping("/details/{accountId}")
    public ResponseEntity<DetailsBankAccountDto> detailsAccount(
            @PathVariable("accountId") UUID accountId,
            @RequestParam("pin") String pin) {

        DetailsBankAccountDto accountDto = new DetailsBankAccountDto(
                UUID.randomUUID(),
                "Solodkov",
                BigDecimal.valueOf(0.0),
                "usd"
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountDto);
    }

    /**
     * Создание нового счета
     *
     * @param bankAccountDto данные необходимые для создания счета
     * @param pin            пин код
     * @return возвращает ответ в виде JSON
     */
    @Override
    @PostMapping("/add")
    public ResponseEntity<DetailsBankAccountDto> addAccount(
            @Valid @RequestBody BankAccountDto bankAccountDto,
            @RequestParam("pin") String pin) {

        DetailsBankAccountDto accountDto = new DetailsBankAccountDto(
                UUID.randomUUID(),
                "Solodkov",
                BigDecimal.valueOf(0.0),
                "usd"
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountDto);
    }

    /**
     * Операции со счетами:
     * переводы, вывод, добавление
     *
     * @param transferDto данные необходимые для транзакций
     * @param pin         пин код
     * @return возвращает ответ в виде JSON
     */
    @Override
    @PatchMapping("/transfer")
    public ResponseEntity<DetailsBankAccountDto> transfer(
            @Valid @RequestBody TransferDto transferDto,
            @RequestParam("pin") String pin) {

        DetailsBankAccountDto accountDto = new DetailsBankAccountDto(
                UUID.randomUUID(),
                "Solodkov",
                BigDecimal.valueOf(0.0),
                "usd"
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountDto);
    }
}
