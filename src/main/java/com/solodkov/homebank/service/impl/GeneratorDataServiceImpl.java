package com.solodkov.homebank.service.impl;

import com.solodkov.homebank.model.BankAccount;
import com.solodkov.homebank.model.User;
import com.solodkov.homebank.repository.UserRepository;
import com.solodkov.homebank.service.GeneratorDataService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class GeneratorDataServiceImpl implements GeneratorDataService {

  final UserRepository userRepository;

  @Override
  public void generateData() {

    List<User> users = new ArrayList<>();
    users.add(new User(
        0,
        "Grisha",
        "1111",
        new ArrayList<>()
    ));

    users.add(new User(
        0,
        "Fedia",
        "2222",
        new ArrayList<>()
    ));

    users.add(new User(
        0,
        "Dasha",
        "3333",
        new ArrayList<>()
    ));

    users.forEach(user -> {
      for (int i = 0; i < 2; i++) {
        user.getBankAccounts().add(
            new BankAccount(
                0,
                UUID.randomUUID(),
                user,
                BigDecimal.TEN
            )
        );
      }
    });

    userRepository.saveAll(users);

  }
}
