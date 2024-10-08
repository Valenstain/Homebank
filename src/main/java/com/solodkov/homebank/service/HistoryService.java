package com.solodkov.homebank.service;

import com.solodkov.homebank.dto.HistoryDto;
import com.solodkov.homebank.dto.TransferDto;
import com.solodkov.homebank.model.User;

import java.util.List;

public interface HistoryService {

  /**
   * Получение истории операций по
   * счету пользователя
   *
   * @param username имя пользователя
   * @param pin      пин
   * @return список произведенных операций
   */
  List<HistoryDto> getHistory(String username, String pin);

  /**
   * Добавление операции в БД
   *
   * @param transferDto проводимая операция
   * @param user        имя пользователя
   */
  void saveHistory(TransferDto transferDto, User user);
}
