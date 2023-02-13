package com.cryptoapp.service;

import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.dto.mapper.UserMapper;
import com.cryptoapp.model.User;
import com.cryptoapp.model.Wallet;
import com.cryptoapp.repository.UserRepo;
import com.cryptoapp.repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final WalletService walletService;
    private final WalletRepo walletRepo;

    @Autowired
    public UserService(UserRepo userRepo, WalletService walletService,
                       WalletRepo walletRepo) {
        this.userRepo = userRepo;
        this.walletService = walletService;
        this.walletRepo = walletRepo;
    }

    public UserDTO mapAndSave(UserDTO userDTO) {
        User user = mapToUser(userDTO);
        userRepo.save(mapToUser(userDTO));
        return mapToDTO(user);
    }

    public UserDTO changeEmail(Long idUser, String email) {
        User user = userRepo.findById(idUser).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setEmail(email);
        userRepo.save(user);
        return mapToDTO(user);
    }

    public User mapToUser(UserDTO userDTO) {
        return UserMapper.mapToUser(userDTO);
    }

    public UserDTO mapToDTO(User user) {
        return UserMapper.mapToDTO(user);
    }

    public UserDTO changePassword(Long idUser, String password) {
        User user = userRepo.findById(idUser).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setPassword(password);
        userRepo.save(user);
        return mapToDTO(user);
    }

    public void deleteUser(Long idUser) {
        userRepo.deleteById(idUser);
    }

    public WalletDTO addWalletToUser(Long userId, Long walletId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        wallet.setUser(user);
        walletRepo.save(wallet);
        return walletService.mapToDTO(wallet);
    }

    public List<UserDTO> getUserList() {
        List<User> userList = userRepo.findAll();
        ArrayList<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = mapToDTO(user);
            userDTOList.add(userDTO);
        }
        return userDTOList;

    }

    public UserDTO getUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapToDTO(user);
    }

    public List<WalletDTO> getWalletByUser(Long idUser) {
        List<Wallet> walletList = userRepo.findById(idUser)
                .map(User::getWallet)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ArrayList<WalletDTO> walletDTOList = new ArrayList<>();
        for (Wallet wallet : walletList) {
            WalletDTO walletDTO = walletService.mapToDTO(wallet);
            walletDTOList.add(walletDTO);
        }
        return walletDTOList;
    }
}
