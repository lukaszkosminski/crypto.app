package com.cryptoapp.model.crypto;

import com.cryptoapp.model.CryptoCurrency;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Eth extends CryptoCurrency {

    private String name;
    private BigDecimal price;
    private String symbol;

    public Eth(BigDecimal price) {
        this.name = "ETH";
        this.price = price;
        this.symbol = "ETH";
    }

    public Eth() {
    }
}
