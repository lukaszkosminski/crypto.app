package com.cryptoapp.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class CryptoCurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private Timestamp createdTime;

    @ManyToOne
    @JoinColumn(name = "cryptocurrency_id")
    private CryptoCurrency cryptoCurrency;

    private BigDecimal price;

    }
