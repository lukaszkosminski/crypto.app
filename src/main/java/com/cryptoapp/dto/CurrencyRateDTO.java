package com.cryptoapp.dto;

import com.cryptoapp.model.Currency;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Getter
@Setter
public class CurrencyRateDTO {

    private Currency currency;

    private BigDecimal price;

    public CurrencyRateDTO(Currency currency, BigDecimal price) {
        this.currency = currency;
        this.price = price;
    }
}
