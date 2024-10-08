package com.solodkov.homebank.controller.impl;

import com.solodkov.homebank.controller.GenerateDataController;
import com.solodkov.homebank.service.GeneratorDataService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController("/homebank/generator")
public class GenerateDataControllerImpl implements GenerateDataController {

  final GeneratorDataService generatorDataService;

  @Override
  @GetMapping("/run")
  public ResponseEntity<HttpStatus> generateData() {

    generatorDataService.generateData();

    return ResponseEntity.ok(HttpStatus.OK);
  }
}
