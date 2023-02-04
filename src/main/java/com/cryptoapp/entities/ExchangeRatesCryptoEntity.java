package com.cryptoapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ExchangeRatesCryptoEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Integer btcRate;
    private Integer ethRate;
    private Integer bnbRate;
    private Integer xrpRate;
    private Integer adaRate;
    private Integer dogeRate;
    private Integer hexRate;
    private Integer maticValue;
    private Integer solValue;
    private Integer shibValue;

}
