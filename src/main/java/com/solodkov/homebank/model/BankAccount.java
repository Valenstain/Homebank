package com.solodkov.homebank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

import static lombok.AccessLevel.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
@Entity
@ToString
@Table(name = "bank_account")
public class BankAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @NotNull
  @Column(name = "account_id")
  UUID accountId;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
      CascadeType.DETACH})
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  User user;

  @Column(name = "balance")
  BigDecimal balance;
}
