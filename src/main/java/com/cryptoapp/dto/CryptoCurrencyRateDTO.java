package com.cryptoapp.dto;

import com.cryptoapp.model.CryptoCurrency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CryptoCurrencyRateDTO {

    private BigDecimal price;

    private CryptoCurrency cryptoCurrency;


    public CryptoCurrencyRateDTO(BigDecimal price, CryptoCurrency cryptoCurrency) {
        this.price = price;
        this.cryptoCurrency = cryptoCurrency;
    }

}
