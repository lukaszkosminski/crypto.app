package com.cryptoapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class Currency {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;

    private String name;
    @PositiveOrZero
    private BigDecimal quantity;

    @ManyToOne
    private Wallet wallet;

}
