package com.cryptoapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private TransactionType transactionType;

//    @OneToOne
//    private CryptoCurrency cryptoCurrency;
//
//    @OneToOne
//    private Currency currency;

    private BigDecimal amount;

    private BigDecimal price;

    @CreationTimestamp
    private Timestamp createdTime;

}
