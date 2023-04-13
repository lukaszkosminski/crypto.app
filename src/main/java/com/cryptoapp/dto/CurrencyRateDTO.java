package com.cryptoapp.dto;

import com.cryptoapp.model.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class CurrencyRateDTO {

    private Currency currency;

    private BigDecimal price;

    private String sellSymbol;

    public CurrencyRateDTO(Currency currency, BigDecimal price, String sellSymbol) {
        this.currency = currency;
        this.price = price;
        this.sellSymbol = sellSymbol;
    }
}
