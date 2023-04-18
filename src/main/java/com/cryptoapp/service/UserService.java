package com.cryptoapp.service;

import com.cryptoapp.dto.CurrencyDTO;
import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.dto.mapper.CurrencyMapper;
import com.cryptoapp.dto.mapper.UserMapper;
import com.cryptoapp.dto.mapper.ValueMapper;
import com.cryptoapp.dto.mapper.WalletMapper;
import com.cryptoapp.model.Currency;
import com.cryptoapp.model.User;
import com.cryptoapp.model.Value;
import com.cryptoapp.model.Wallet;
import com.cryptoapp.repository.UserRepo;
import com.cryptoapp.repository.ValueRepo;
import com.cryptoapp.repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final WalletService walletService;
    private final WalletRepo walletRepo;

    private final CurrencyService currencyService;

    private final ValueService valueService;
    private final ValueRepo valueRepo;

    @Autowired
    public UserService(UserRepo userRepo, WalletService walletService, WalletRepo walletRepo, CurrencyService currencyService, ValueService valueService, ValueRepo valueRepo) {
        this.userRepo = userRepo;
        this.walletService = walletService;
        this.walletRepo = walletRepo;
        this.currencyService = currencyService;
        this.valueService = valueService;
        this.valueRepo = valueRepo;
    }

    public UserDTO save(UserDTO userDTO) {
        userRepo.save(UserMapper.mapToUser(userDTO));
        return userDTO;
    }

    public UserDTO changeEmail(UserDTO userDTO, User userAuth) {
        User user = userRepo.findByLogin(userAuth.getLogin());
        user.setEmail(userAuth.getEmail());
        userRepo.save(user);
        return UserMapper.mapToDTO(user);
    }

    public UserDTO changePassword(User userAuth, UserDTO userDTO) {
        if (userDTO.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        User user = userRepo.findByLogin(userAuth.getLogin());
        user.setPassword(userDTO.getPassword());
        user.hashPassword(user.getPassword());
        userRepo.save(user);
        return UserMapper.mapToDTO(user);
    }
    @Transactional
    public void deleteUser(Long idUser) {
        User user = userRepo.findById(idUser).orElseThrow();
        List<Wallet> allByUser = walletRepo.findAllByUser(user);

        for(Wallet wallet : allByUser){
            valueRepo.deleteByWallet(wallet);
        }
        walletRepo.deleteByUser(user);
        userRepo.delete(user);
    }

    public WalletDTO addWalletToUser(User userAuth, WalletDTO walletDTO) {
        User user = userRepo.findByLogin(userAuth.getLogin());
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

    public User getUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user;
    }

public List<WalletDTO> getWalletsByUser(User userAuth) {

    User user = userRepo.findByLogin(userAuth.getLogin());

    List<Wallet> walletList = user.getWalletList();
    List<WalletDTO> walletDTOList = new ArrayList<>();

    for (Wallet wallet : walletList) {
        WalletDTO walletDTO = WalletMapper.mapToDTO(wallet);
        walletDTOList.add(walletDTO);
        System.out.println(wallet);
    }
    return walletDTOList;
}


    public UserDTO createUserWithPreferredCurrency(UserDTO userDTO) throws Exception {
        User existingUserEmail = userRepo.findByEmail(userDTO.getEmail());
        if (existingUserEmail != null) {
            throw new Exception("User with this email already exists");
        }
        User existingUserLogin = userRepo.findByLogin(userDTO.getLogin());
        if (existingUserLogin != null) {
            throw new Exception("User with this login already exists");
        }


        User user = userRepo.save(UserMapper.mapToUser(userDTO));
        if (user == null) {
            throw new Exception("Unable to create user");
        }
        Wallet walletDefalut = new Wallet();
        walletDefalut.setName("default");


        Wallet wallet  =walletService.save(walletDefalut);
        if (wallet == null) {
            throw new Exception("Unable to create wallet for user");
        }
        wallet.setUser(user);
        walletService.save(wallet);
        ArrayList<Wallet> walletArrayList = new ArrayList<>();
        walletArrayList.add(wallet);
        user.setWalletList(walletArrayList);
        userRepo.save(user);
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setSymbol(userDTO.getPreferredCurrency());
        currencyDTO.setIsCrypto(false);
        Currency currency = currencyService.save(CurrencyMapper.mapToCurrency(currencyDTO));

        Value value = new Value();
        value.setCurrency(currency);
        value.setWallet(wallet);
        value.setQuantity(BigDecimal.valueOf(0));
        valueService.save(ValueMapper.mapToDTO(value));

        return UserMapper.mapToDTO(user);
    }



}
