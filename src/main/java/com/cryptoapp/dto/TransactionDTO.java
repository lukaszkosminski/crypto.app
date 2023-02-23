package com.cryptoapp.dto;

import com.cryptoapp.model.CryptoCurrency;
import com.cryptoapp.model.Currency;
import com.cryptoapp.model.TransactionType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class TransactionDTO {


    private TransactionType transactionType;

//    @OneToOne
//    private CryptoCurrency cryptoCurrency;
//
//    @OneToOne
//    private Currency currency;

    private BigDecimal amount;

    private BigDecimal price;


}
