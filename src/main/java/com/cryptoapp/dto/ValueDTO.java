package com.cryptoapp.dto;

import com.cryptoapp.model.Currency;
import com.cryptoapp.model.Wallet;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ValueDTO {

    private Currency currency;


    private BigDecimal quantity;


    private Wallet wallet;

}
