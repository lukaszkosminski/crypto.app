package com.cryptoapp.model.crypto;

import com.cryptoapp.model.CryptoCurrency;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Btc extends CryptoCurrency {

    private String name;
    private BigDecimal price;
    private String symbol;

    public Btc(BigDecimal price) {
        this.name = "BTC";
        this.price = price;
        this.symbol = "BTC";
    }

    public Btc() {
    }
}
