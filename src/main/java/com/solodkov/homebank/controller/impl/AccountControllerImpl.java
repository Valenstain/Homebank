package com.solodkov.homebank.controller.impl;

import com.solodkov.homebank.controller.AccountController;
import com.solodkov.homebank.dto.BankAccountDto;
import com.solodkov.homebank.dto.DetailsBankAccountDto;
import com.solodkov.homebank.dto.TransferDto;
import com.solodkov.homebank.dto.UserBankAccountsDto;
import com.solodkov.homebank.service.AccountService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.*;

@Validated
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@RestController("/homebank/bank-accounts")
public class AccountControllerImpl implements AccountController {

  final AccountService accountService;

  @Override
  @GetMapping("/all")
  public ResponseEntity<List<UserBankAccountsDto>> getAllBankAccounts() {

    List<UserBankAccountsDto> bankAccounts = accountService.getAllBankAccounts();

    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(bankAccounts);
  }

  @Override
  @GetMapping("/{username}")
  public ResponseEntity<UserBankAccountsDto> getUserBankAccounts(
      @PathVariable("username") String username,
      @RequestParam("pin") String pin) {

    UserBankAccountsDto userBankAccountsDto = accountService.getUserBankAccounts(username, pin);

    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userBankAccountsDto);
  }

  @Override
  @PostMapping("/add")
  public ResponseEntity<DetailsBankAccountDto> addAccount(
      @Valid @RequestBody BankAccountDto bankAccountDto) {

    DetailsBankAccountDto detailsBankAccountDto = accountService.addBankAccount(bankAccountDto);

    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(detailsBankAccountDto);
  }

  @Override
  @PatchMapping("/transfer")
  public ResponseEntity<DetailsBankAccountDto> transfer(
      @Valid @RequestBody TransferDto transferDto) {

    DetailsBankAccountDto accountDto = accountService.handleTransfer(transferDto);

    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(accountDto);
  }

}
