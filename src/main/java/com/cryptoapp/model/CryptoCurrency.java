package com.cryptoapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class CryptoCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String symbol;

    private BigDecimal quantity;

    @OneToMany(mappedBy = "cryptoCurrency", cascade = CascadeType.ALL)
    private List<CryptoCurrencyRate> rates;

}
