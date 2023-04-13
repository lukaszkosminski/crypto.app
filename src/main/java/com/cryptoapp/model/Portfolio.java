package com.cryptoapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Portfolio {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal totalAssetBalance;

    private BigDecimal cryptoCurrencyAssetBalance;

    private BigDecimal currencyAssetBalance;
}
