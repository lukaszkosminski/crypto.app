package com.cryptoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

}
