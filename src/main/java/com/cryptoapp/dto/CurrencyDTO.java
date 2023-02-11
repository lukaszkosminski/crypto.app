package com.cryptoapp.dto;

import com.cryptoapp.model.Wallet;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CurrencyDTO {
    private String symbol;
    private String name;
    private Integer quantity;
    private Wallet wallet;

}
