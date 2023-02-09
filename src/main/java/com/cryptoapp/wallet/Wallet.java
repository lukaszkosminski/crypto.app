package com.cryptoapp.wallet;

import com.cryptoapp.currency.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
