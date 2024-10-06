package com.solodkov.homebank.model;

import com.solodkov.homebank.enums.AccountOperation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    BankAccount bankAccount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "operation")
    AccountOperation operation;

    @Column(name = "amount")
    BigDecimal amount;
}
