package com.cryptoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long idUser;

    private String login;

    private String email;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Wallet> wallet;

}
