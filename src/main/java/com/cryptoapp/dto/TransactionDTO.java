package com.cryptoapp.dto;

import com.cryptoapp.model.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDTO {


    private TransactionType transactionType;


//    private Integer amount;

    private BigDecimal priceUsd;



//    private Value value;

//    private Wallet wallet;

    // baseCurrency/quoteCurrency

    private String baseCurrencySymbol;

    private BigDecimal baseCurrencyAmount;

    private String quoteCurrencySymbol;

    private BigDecimal quoteCurrencyAmount;

    public TransactionDTO() {
    }


}
