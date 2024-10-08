package com.solodkov.homebank.controller;

import com.solodkov.homebank.dto.BankAccountDto;
import com.solodkov.homebank.dto.DetailsBankAccountDto;
import com.solodkov.homebank.dto.TransferDto;
import com.solodkov.homebank.dto.UserBankAccountsDto;
import com.solodkov.homebank.handler.exception.BadRequestException;
import com.solodkov.homebank.handler.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Контроллер по работе со счетами
 */
@Tag(name = "Account Controller",
    description = "Используется для работы со счетами")
public interface AccountController {

  /**
   * Полечение всех счетов всех пользователей
   *
   * @return ответ в виде JSON
   */
  @Operation(
      summary = "Полечение всех счетов всех пользователей"
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "Получение списка счетов выполнено успешно",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserBankAccountsDto.class)
              )
          )
      }
  )
  @GetMapping("/all")
  ResponseEntity<List<UserBankAccountsDto>> getAllBankAccounts();

  /**
   * Получение списка всех счетов закрепленных за пользователем
   *
   * @param username имя пользователя
   * @param pin      пин код
   * @return ответ в виде JSON
   */
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
                  schema = @Schema(implementation = UserBankAccountsDto.class)
              )
          ),
          @ApiResponse(
              responseCode = "404",
              description = "Запись не найдена",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NotFoundException.class)
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Некоректные данные",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BadRequestException.class)
              )
          )
      }
  )
  @GetMapping("/{username}")
  ResponseEntity<UserBankAccountsDto> getUserBankAccounts(
      @Parameter(
          name = "username",
          description = "Имя пользователя")
      String username,
      @Parameter(
          name = "pin",
          description = "Пин код для осуществления операции")
      String pin
  );

  /**
   * Создание нового счета
   *
   * @param bankAccountDto данные необходимые для создания счета
   * @return возвращает ответ в виде JSON
   */
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
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Некоректные данные",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BadRequestException.class)
              )
          )
      }
  )
  @PostMapping("/add")
  ResponseEntity<DetailsBankAccountDto> addAccount(
      @Valid
      @RequestBody(
          description = "Для создания счета укажите имя пользователя (username), " +
              "пин код (pin), который необходим для соверщения операций, начальное кол-во " +
              "активов на счету (amount).")
      BankAccountDto bankAccountDto);

  /**
   * Операции со счетами: переводы, вывод, добавление
   *
   * @param transferDto данные необходимые для транзакций
   * @return возвращает ответ в виде JSON
   */
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
          ),
          @ApiResponse(
              responseCode = "404",
              description = "Запись не найдена",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NotFoundException.class)
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Некоректные данные",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BadRequestException.class)
              )
          )
      }
  )
  @PatchMapping("/transfer")
  ResponseEntity<DetailsBankAccountDto> transfer(
      @Valid
      @RequestBody(
          description = "Данные для переводов, вывода, добавления. Для перевода необхоидмо " +
              "выбрать TRANSFER и указать счет откуда (fromAccountId) и куда (toAccountId) " +
              "будет осуществлен перевод, для вывода необходимо выбрать WITHDRAW и указать " +
              "номер счета вывода (fromAccountId), для добавления средств необходимо выбрать " +
              "ADD и указать счет для зачисления (toAccountId). Для успешной операции необходимо " +
              "также указать PIN и количество")
      TransferDto transferDto);
}
