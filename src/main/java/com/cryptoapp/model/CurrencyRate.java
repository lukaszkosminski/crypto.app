package com.cryptoapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Getter
@Setter
@Entity
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private Timestamp createdTime;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @PositiveOrZero
    private BigDecimal price;
}
