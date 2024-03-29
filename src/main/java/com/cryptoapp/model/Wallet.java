package com.cryptoapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString

public class Wallet {

    @Id
    @GeneratedValue
    private Long idWallet;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "wallet")
    private List<Value> valueList;

//    @OneToMany(mappedBy = "wallet")
//    private List<Transaction> transactionList;

}
