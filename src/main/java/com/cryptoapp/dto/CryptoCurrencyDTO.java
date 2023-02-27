package com.cryptoapp.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CryptoCurrencyDTO {

    private String name;

    private String symbol;
    @PositiveOrZero
    private BigDecimal quantity;

}