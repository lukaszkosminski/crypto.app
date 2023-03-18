package com.cryptoapp.controller;

import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.dto.mapper.UserMapper;
import com.cryptoapp.model.User;
import com.cryptoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("api/user/change-email")
    public UserDTO changeEmail(@AuthenticationPrincipal User user, @RequestBody UserDTO userDTO) {
        return userService.changeEmail(userDTO,user);
    }

    @PutMapping("api/user/change-password")
    public UserDTO changePassword(@AuthenticationPrincipal User user, @RequestBody UserDTO userDTO) {
        return userService.changePassword(user, userDTO);
    }

    @DeleteMapping("api/admin/user/{idUser}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long idUser) {
        userService.deleteUser(idUser);
    }

    @PutMapping("api/user/wallet")
    public WalletDTO addWalletToUser(@AuthenticationPrincipal User user, @RequestBody WalletDTO walletDTO) {
        return userService.addWalletToUser(user, walletDTO);
    }

    @GetMapping("api/admin/users")
    public List<UserDTO> getAllUser() {
        return userService.getUserList();
    }

    @GetMapping("api/admin/user/{idUser}")
    public UserDTO getUser(@PathVariable Long idUser) {
        return UserMapper.mapToDTO(userService.getUser(idUser));
    }

    @GetMapping("api/user/wallets")
    public List<WalletDTO> getWalletsByUser(@AuthenticationPrincipal User user) {
        return userService.getWalletsByUser(user);
    }


    @PostMapping("api/public/create-default-wallet")
    public UserDTO createUserWithPreferredCurrencyWallet(@RequestBody UserDTO userDTO) throws Exception {
        return userService.createUserWithPreferredCurrency(userDTO);

    }

}
