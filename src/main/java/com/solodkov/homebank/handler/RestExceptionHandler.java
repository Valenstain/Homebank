package com.solodkov.homebank.handler;

import com.solodkov.homebank.dto.ResponseExceptionDto;
import com.solodkov.homebank.handler.exception.BadRequestException;
import com.solodkov.homebank.handler.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<ResponseExceptionDto> handleNotFoundException(NotFoundException e) {

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ResponseExceptionDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<ResponseExceptionDto> handleBadRequestException(BadRequestException e) {

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ResponseExceptionDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
  }
}
