package com.cryptoapp.currency;

import com.cryptoapp.wallet.Wallet;
import jakarta.persistence.*;
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
