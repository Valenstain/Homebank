package com.solodkov.homebank.controller;

import com.solodkov.homebank.dto.HistoryDto;
import com.solodkov.homebank.handler.exception.BadRequestException;
import com.solodkov.homebank.handler.exception.NotFoundException;
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

/**
 * Контроллер предоставляющий исторические данные по операциям на счете
 */
@Tag(name = "History Controller",
    description = "Этот контроллер предназначен для работы с иторией транзакций")
public interface HistoryController {

  /**
   * Получение истории транзакций
   *
   * @param username имя пользователя
   * @param pin      пин код
   * @return возвращает ответ в виде JSON
   */
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
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Записи не найдены",
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
  })
  @GetMapping("/history")
  ResponseEntity<List<HistoryDto>> getHistory(
      @Parameter(name = "username",
          description = "Имя пользователя")
      String username,
      @Parameter(name = "pin",
          description = "Пин код для осуществления операции")
      String pin);
}
