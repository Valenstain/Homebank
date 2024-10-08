package com.solodkov.homebank.service.impl;

import static lombok.AccessLevel.*;

import com.solodkov.homebank.dto.HistoryDto;
import com.solodkov.homebank.dto.TransferDto;
import com.solodkov.homebank.handler.exception.BadRequestException;
import com.solodkov.homebank.handler.exception.NotFoundException;
import com.solodkov.homebank.mapper.HistoryMapper;
import com.solodkov.homebank.model.History;
import com.solodkov.homebank.model.User;
import com.solodkov.homebank.repository.HistoryRepository;
import com.solodkov.homebank.repository.UserRepository;
import com.solodkov.homebank.service.HistoryService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Service
public class HistoryServiceImpl implements HistoryService {

  final UserRepository userRepository;
  final HistoryRepository historyRepository;
  final HistoryMapper historyMapper;


  @Override
  public List<HistoryDto> getHistory(String username, String pin) {

    // Проверяем наличие пользователя
    User user = userRepository.findByName(username)
        .orElseThrow(() -> new NotFoundException("Данные не найдены"));

    // Проверяем pin
    if (!user.getPin().equals(pin)) {
      throw new BadRequestException("Некорректный pin");
    }

    // Получаем историю
    List<History> histories = historyRepository.findByUserId(user.getId());

    // Возвращаем результат
    return historyMapper.toHistoryDto(histories);
  }

  @Override
  public void saveHistory(TransferDto transferDto, User user) {

    // Мапим данные с dto
    History history = historyMapper.toHistory(transferDto);

    // Задаем пользователя
    history.setUser(user);

    // Сохраняем в базе данных
    historyRepository.save(history);
  }
}
