package com.solodkov.homebank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Генератор фейковых данных в БД
 */
@Tag(name = "Generate Data Controller",
    description = "Используется для загрузки фейковых данных в БД")
public interface GenerateDataController {

  /**
   * Генерация данных в БД для проверки работы
   *
   * @return статус ответа
   */
  @Operation(
      summary = "Генерация данных в БД для проверки работы"
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "Данные успешно созданы",
              content = @Content
          )
      }
  )
  @GetMapping("/run")
  ResponseEntity<HttpStatus> generateData();

}
