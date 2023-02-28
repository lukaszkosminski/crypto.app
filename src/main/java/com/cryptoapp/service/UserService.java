package com.cryptoapp.service;

import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.dto.mapper.UserMapper;
import com.cryptoapp.dto.mapper.WalletMapper;
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
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final WalletService walletService;
    private final WalletRepo walletRepo;

    @Autowired
    public UserService(UserRepo userRepo, WalletService walletService, WalletRepo walletRepo) {
        this.userRepo = userRepo;
        this.walletService = walletService;
        this.walletRepo = walletRepo;
    }

    public UserDTO save(UserDTO userDTO) {
        userRepo.save(UserMapper.mapToUser(userDTO));
        return userDTO;
    }

    public UserDTO changeEmail(Long idUser, UserDTO userDTO) {
        User user = userRepo.findById(idUser).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setEmail(userDTO.getEmail());
        userRepo.save(user);
        return UserMapper.mapToDTO(user);
    }

    public UserDTO changePassword(Long idUser, UserDTO userDTO) {
        User user = userRepo.findById(idUser).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setPassword(userDTO.getPassword());
        userRepo.save(user);
        return UserMapper.mapToDTO(user);
    }

    public void deleteUser(Long idUser) {
        userRepo.deleteById(idUser);
    }

    public WalletDTO addWalletToUser(Long userId, WalletDTO walletDTO) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Wallet wallet = WalletMapper.mapToWallet(walletDTO);
        wallet.setUser(user);
        walletService.save(wallet);
        return WalletMapper.mapToDTO(wallet);
    }

    public List<UserDTO> getUserList() {
        List<User> userList = userRepo.findAll();
        ArrayList<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = UserMapper.mapToDTO(user);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public UserDTO getUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return UserMapper.mapToDTO(user);
    }

    public List<WalletDTO> getWalletsByUser(Long idUser) {
        List<Wallet> walletList = userRepo.findById(idUser).map(User::getWallet).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ArrayList<WalletDTO> walletDTOList = new ArrayList<>();
        for (Wallet wallet : walletList) {
            WalletDTO walletDTO = WalletMapper.mapToDTO(wallet);
            walletDTOList.add(walletDTO);
        }
        return walletDTOList;
    }

    public WalletDTO addWalletToUser(Long userId, Long idWallet) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Wallet wallet = walletRepo.findById(idWallet).orElseThrow();
        wallet.setUser(user);
        walletService.save(wallet);
        return WalletMapper.mapToDTO(wallet);
    }

}
