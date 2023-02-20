package com.cryptoapp.controller;

import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("users/create")
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @PutMapping("user/{idUser}/change-email")
    public UserDTO changeEmail(@PathVariable Long idUser, @RequestBody UserDTO userDTO) {
        return userService.changeEmail(idUser, userDTO);
    }

    @PutMapping("user/{idUser}/change-password")
    public UserDTO changePassword(@PathVariable Long idUser, @RequestBody UserDTO userDTO) {
        return userService.changePassword(idUser, userDTO);
    }

    @DeleteMapping("user/{idUser}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long idUser) {
        userService.deleteUser(idUser);
    }

    @PutMapping("user/{idUser}/wallet")
    public WalletDTO addWalletToUser(@PathVariable Long idUser, @RequestBody WalletDTO walletDTO) {
        return userService.addWalletToUser(idUser, walletDTO);
    }

    @GetMapping("user")
    public List<UserDTO> getAllUser() {
        return userService.getUserList();
    }

    @GetMapping("user/{idUser}")
    public UserDTO getUser(@PathVariable Long idUser) {
        return userService.getUser(idUser);
    }

    @GetMapping("user/{idUser}/wallets")
    public List<WalletDTO> getWalletsByUser(@PathVariable Long idUser) {
        return userService.getWalletsByUser(idUser);
    }

}
