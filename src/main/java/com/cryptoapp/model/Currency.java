package com.cryptoapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Currency {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;

    private Boolean isCrypto;


}
