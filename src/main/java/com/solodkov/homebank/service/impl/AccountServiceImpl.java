package com.solodkov.homebank.service.impl;

import com.solodkov.homebank.dto.BankAccountDto;
import com.solodkov.homebank.dto.DetailsBankAccountDto;
import com.solodkov.homebank.dto.TransferDto;
import com.solodkov.homebank.dto.UserBankAccountsDto;
import com.solodkov.homebank.handler.exception.BadRequestException;
import com.solodkov.homebank.handler.exception.NotFoundException;
import com.solodkov.homebank.mapper.BankAccountMapper;
import com.solodkov.homebank.model.BankAccount;
import com.solodkov.homebank.model.User;
import com.solodkov.homebank.repository.BankAccountRepository;
import com.solodkov.homebank.repository.UserRepository;
import com.solodkov.homebank.service.AccountService;
import com.solodkov.homebank.service.HistoryService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.*;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Service
public class AccountServiceImpl implements AccountService {

  final BankAccountRepository accountRepository;
  final UserRepository userRepository;
  final BankAccountMapper bankAccountMapper;
  final HistoryService historyService;

  @Override
  public List<UserBankAccountsDto> getAllBankAccounts() {

    // Получаем данные всех пользователей с их счетами
    List<User> users = userRepository.findAll();

    // Возвращаем результат
    return users.stream()
        .map(user -> new UserBankAccountsDto(
            user.getName(),
            bankAccountMapper.toDetailsBankAccountDtoList(user.getBankAccounts()))
        ).toList();
  }

  @Override
  public UserBankAccountsDto getUserBankAccounts(String username, String pin) {

    // Получаем пользователя со счетами
    User user = userRepository.findByName(username)
        .orElseThrow(() -> new NotFoundException("Пользователь на найлен"));

    // Проверяем pin
    checkPin(user.getPin(), pin);

    // Возвращаем результат
    return new UserBankAccountsDto(
        user.getName(),
        bankAccountMapper.toDetailsBankAccountDtoList(user.getBankAccounts())
    );
  }

  @Override
  public DetailsBankAccountDto addBankAccount(BankAccountDto bankAccountDto) {

    if (!bankAccountDto.pin().matches("\\d{4}") ||
        !bankAccountDto.username().matches("^[A-Za-zА-Яа-я ]{4,30}$")) {
      throw new BadRequestException("Некорректные данные. Имя должно состоять "
          + " от 4 до 30 букв. Pin должен состоять из 4 цифр");
    }

    // Мапим данные с dto
    BankAccount bankAccount = bankAccountMapper.toBankAccount(bankAccountDto);

    // Получаем пользователя с БД
    User user = userRepository.findByNameAndPin(
            bankAccount.getUser().getName(),
            bankAccount.getUser().getPin())
        .orElse(
            new User(
                0,
                bankAccount.getUser().getName(),
                bankAccount.getUser().getPin(),
                null)
        );

    // Структурируем данные для запроса
    bankAccount.setAccountId(UUID.randomUUID());
    bankAccount.setUser(user);

    // Выполняем добавление
    accountRepository.save(bankAccount);

    // Возвращаем результат
    return bankAccountMapper.toDetailsBankAccountDto(bankAccount);
  }

  @Override
  public DetailsBankAccountDto handleTransfer(TransferDto transferDto) {

    // Перенаправляем запрос в зависимости от типа операции
    return switch (transferDto.operation()) {
      case WITHDRAW -> transferWithdraw(transferDto);
      case TRANSFER -> transferMoney(transferDto);
      case ADD -> transferAdd(transferDto);
    };
  }

  @Override
  public DetailsBankAccountDto transferMoney(TransferDto transferDto) {

    // Получаем данные счета откуда
    BankAccount fromBankAccount = accountRepository.findByAccountId(transferDto.fromAccountId())
        .orElseThrow(() -> new NotFoundException("Счет отправителя не найден"));

    // Проверяем pin
    checkPin(fromBankAccount.getUser().getPin(), transferDto.pin());

    // Получаем данные счета куда
    BankAccount toBankAccount = accountRepository.findByAccountId(transferDto.toAccountId())
        .orElseThrow(() -> new NotFoundException("Счет получателя не найден"));

    // Расчитываем балансы
    BigDecimal fromAmount = fromBankAccount.getBalance().subtract(transferDto.amount());
    BigDecimal toAmount = toBankAccount.getBalance().add(transferDto.amount());

    // Сетим новые балансы
    fromBankAccount.setBalance(fromAmount);
    toBankAccount.setBalance(toAmount);

    // Сохраняем в БД
    accountRepository.save(fromBankAccount);
    accountRepository.save(toBankAccount);

    // Пишем транзакцию в истории
    historyService.saveHistory(transferDto, fromBankAccount.getUser());

    // Возвращаем результат
    return bankAccountMapper.toDetailsBankAccountDto(fromBankAccount);
  }

  @Override
  public DetailsBankAccountDto transferWithdraw(TransferDto transferDto) {

    // Получаем данные счета откуда
    BankAccount fromBankAccount = accountRepository.findByAccountId(transferDto.fromAccountId())
        .orElseThrow(() -> new NotFoundException("Счет для вывода средств не найден"));

    // Проверяем pin
    checkPin(fromBankAccount.getUser().getPin(), transferDto.pin());

    // Расчитываем баланс
    BigDecimal fromAmount = fromBankAccount.getBalance().subtract(transferDto.amount());

    // Сетим баланс
    fromBankAccount.setBalance(fromAmount);

    // Сохраняем в БД
    accountRepository.save(fromBankAccount);

    // Пишем транзакцию в истории
    historyService.saveHistory(transferDto, fromBankAccount.getUser());

    // Возвращаем результат
    return bankAccountMapper.toDetailsBankAccountDto(fromBankAccount);
  }

  @Override
  public DetailsBankAccountDto transferAdd(TransferDto transferDto) {

    // Получаем данные счета куда
    BankAccount toBankAccount = accountRepository.findByAccountId(transferDto.toAccountId())
        .orElseThrow(() -> new NotFoundException("Счет для зачисления средств не найден"));

    // Проверяем pin
    if (!toBankAccount.getUser().getPin().equals(transferDto.pin())) {
      throw new BadRequestException("Некорректный pin");
    }

    // Расчитываем баланс
    BigDecimal toAmount = toBankAccount.getBalance().add(transferDto.amount());

    // Сетим баланс
    toBankAccount.setBalance(toAmount);

    // Сохраняем в БД
    accountRepository.save(toBankAccount);

    // Пишем транзакцию в истории
    historyService.saveHistory(transferDto, toBankAccount.getUser());

    // Возвращаем результат
    return bankAccountMapper.toDetailsBankAccountDto(toBankAccount);
  }

  public void checkPin(String checkPin, String pin) {
    if (!checkPin.equals(pin)) {
      throw new BadRequestException("Некорректный pin");
    }
  }
}
