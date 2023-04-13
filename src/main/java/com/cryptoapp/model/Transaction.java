package com.cryptoapp.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

//    private Integer amount;

    private BigDecimal priceUsd;

    @CreationTimestamp
    private Timestamp createdTime;
//    @OneToOne
//    private Value value;
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;
    // baseCurrency/quoteCurrency
//    @OneToOne
    private String baseCurrencySymbol;
    private BigDecimal baseCurrencyAmount;
//    @OneToOne
    private String quoteCurrencySymbol;

    private BigDecimal quoteCurrencyAmount;


}
