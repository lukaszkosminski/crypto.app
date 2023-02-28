package com.cryptoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Wallet {

    @Id
    @GeneratedValue
    private Long idWallet;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "wallet")
    private List<Currency> currency;

    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "wallet")
    private List<CryptoCurrency> cryptoCurrency;

}
