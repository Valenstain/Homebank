package com.solodkov.homebank.controller.impl;

import com.solodkov.homebank.controller.HistoryController;
import com.solodkov.homebank.dto.HistoryDto;
import com.solodkov.homebank.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.*;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@RestController("/homebank")
public class HistoryControllerImpl implements HistoryController {

  final HistoryService historyService;

  @Override
  @GetMapping("/history")
  public ResponseEntity<List<HistoryDto>> getHistory(
      @RequestParam("username") String username,
      @RequestParam("pin") String pin) {

    List<HistoryDto> historyDtoList = historyService.getHistory(
        username,
        pin
    );

    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(historyDtoList);
  }
}
