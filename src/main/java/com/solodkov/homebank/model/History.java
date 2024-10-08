package com.solodkov.homebank.model;

import com.solodkov.homebank.enums.AccountOperation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
@Entity
@Table(name = "history")
public class History {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  User user;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "operation")
  AccountOperation operation;

  @Column(name = "amount")
  BigDecimal amount;

  @Column(name = "account_from")
  UUID accountFrom;

  @Column(name = "account_to")
  UUID accountTo;
}
