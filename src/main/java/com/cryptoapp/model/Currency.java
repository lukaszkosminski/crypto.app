package com.cryptoapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Currency {
    @Id
    @GeneratedValue
    private Long id;
    private String symbol;
    private String name;
    private Integer quantity;
    @ManyToOne
    private Wallet wallet;

}
