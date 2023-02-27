package com.cryptoapp.dto;

import com.cryptoapp.model.Wallet;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class CurrencyDTO {

    private String symbol;

    private String name;
    @PositiveOrZero
    private BigDecimal quantity;

    private Wallet wallet;

}
