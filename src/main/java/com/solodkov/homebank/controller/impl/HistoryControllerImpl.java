package com.solodkov.homebank.controller.impl;

import com.solodkov.homebank.controller.HistoryController;
import com.solodkov.homebank.dto.HistoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Контроллер предоставляющий
 * исторические данные по операциям
 * на счете
 */
@RestController("/homebank")
public class HistoryControllerImpl implements HistoryController {

    /**
     * Получение истории транзакций
     *
     * @param accountId идентификационный номер счета
     * @param pin       пин код
     * @return возвращает ответ в виде JSON
     */
    @Override
    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<HistoryDto>> getHistory(
            @PathVariable("accountId") UUID accountId,
            @RequestParam("pin") String pin) {

        List<HistoryDto> historyDtoList = new ArrayList<>();

        HistoryDto historyDto = new HistoryDto(
                accountId,
                "Solodkov",
                "WITHDRAW",
                BigDecimal.valueOf(12.10),
                "USD");

        historyDtoList.add(historyDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(historyDtoList);
    }
}
