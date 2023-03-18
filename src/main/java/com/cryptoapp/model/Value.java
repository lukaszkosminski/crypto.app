package com.cryptoapp.model;


//import jakarta.persistence.*;
//import jakarta.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@ToString
@Entity
@Getter
@Setter
public class Value {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    private Currency currency;

    @PositiveOrZero
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name ="wallet_id")
    private Wallet wallet;

//    @OneToOne
//    private Transaction transaction;
}
