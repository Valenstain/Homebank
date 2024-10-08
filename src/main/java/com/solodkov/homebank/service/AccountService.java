package com.solodkov.homebank.service;

import com.solodkov.homebank.dto.BankAccountDto;
import com.solodkov.homebank.dto.DetailsBankAccountDto;
import com.solodkov.homebank.dto.TransferDto;

import com.solodkov.homebank.dto.UserBankAccountsDto;
import java.util.List;

public interface AccountService {

  /**
   * Получает все записи из базы данных по счетам и возвращает в виде списка
   *
   * @return список всех счетов всех пользователей
   */
  List<UserBankAccountsDto> getAllBankAccounts();

  /**
   * Получение счетов пользователя
   *
   * @param username имя пользователя
   * @param pin      пин
   * @return данные в виде пользователя и списка его счетов
   */
  UserBankAccountsDto getUserBankAccounts(String username, String pin);

  /**
   * Добавление счета пользователя
   *
   * @param bankAccountDto данные банковского счета
   * @return добавленный банковский счет
   */
  DetailsBankAccountDto addBankAccount(BankAccountDto bankAccountDto);

  /**
   * Главный обработчик запросов на вывод, перевод, зачисления денежных средств на счет
   * пользователя
   *
   * @param transferDto данные для выполнения операции
   * @return данные счета
   */
  DetailsBankAccountDto handleTransfer(TransferDto transferDto);

  /**
   * Операция перевода со счета на счет
   *
   * @param transferDto данные для выполнения операции
   * @return данные счета
   */
  DetailsBankAccountDto transferMoney(TransferDto transferDto);

  /**
   * Операция вывода со счета на счет
   *
   * @param transferDto данные для выполнения операции
   * @return данные счета
   */
  DetailsBankAccountDto transferWithdraw(TransferDto transferDto);

  /**
   * Операция зачисления со счета на счет
   *
   * @param transferDto данные для выполнения операции
   * @return данные счета
   */
  DetailsBankAccountDto transferAdd(TransferDto transferDto);
}
