package com.cryptoapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

    private String login;

    private String email;

    private String password;

    private String preferredCurrency;

    private String role;


}
