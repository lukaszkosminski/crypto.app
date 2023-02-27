package com.cryptoapp.dto;

import com.cryptoapp.model.TransactionType;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDTO {


    private TransactionType transactionType;

    //    @OneToOne
//    private CryptoCurrency cryptoCurrency;
//
//    @OneToOne
//    private Currency currency;
    @PositiveOrZero
    private BigDecimal amount;

    private BigDecimal price;


}
