package com.cryptoapp.controller;

import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.service.UserService;
import com.cryptoapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final WalletService walletService;

    @Autowired
    public UserController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    @PostMapping("user/create")
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.mapAndSave(userDTO);
    }

    @PutMapping("user/{idUser}/change-email/{newEmail}")
    public UserDTO changeEmail(@PathVariable Long idUser, @PathVariable String newEmail) {
        return userService.changeEmail(idUser, newEmail);
    }

    @PutMapping("user/{idUser}/change-password/{newPassword}")
    public UserDTO changePassword(@PathVariable Long idUser, @PathVariable String newPassword) {
        return userService.changePassword(idUser, newPassword);
    }

    @DeleteMapping("user/{idUser}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long idUser) {
        userService.deleteUser(idUser);
    }

    @PutMapping("user/{idUser}/wallet/{idWallet}")
    public WalletDTO addWalletToUser(@PathVariable Long idUser, @PathVariable Long idWallet) {
        return userService.addWalletToUser(idUser, idWallet);
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
        return userService.getWalletByUser(idUser);
    }


}
