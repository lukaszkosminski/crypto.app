package com.cryptoapp.model.crypto;

import com.cryptoapp.model.CryptoCurrency;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Ada extends CryptoCurrency {

    private String name;
    private BigDecimal price;
    private String symbol;


    public Ada(BigDecimal price) {
        this.name = "ADA";
        this.price = price;
        this.symbol = "ADA";
    }

    public Ada() {
    }

}