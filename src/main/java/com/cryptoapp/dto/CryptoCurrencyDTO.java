package com.cryptoapp.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CryptoCurrencyDTO {

    private String name;
    private BigDecimal price;
    private String symbol;


}