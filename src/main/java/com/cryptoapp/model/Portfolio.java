package com.cryptoapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

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
