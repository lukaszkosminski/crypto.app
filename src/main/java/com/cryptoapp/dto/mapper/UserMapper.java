package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.model.User;


public class UserMapper {

    public static User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPreferredCurrency(userDTO.getPreferredCurrency());
        user.setRole(userDTO.getRole());
        return user;
    }

    public static UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPreferredCurrency(user.getPreferredCurrency());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

}
