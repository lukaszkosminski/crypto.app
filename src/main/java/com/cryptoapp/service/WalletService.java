package com.cryptoapp.service;

import com.cryptoapp.dto.WalletDTO;
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

@Service
public class WalletService {

    private final WalletRepo walletRepo;

    private final CurrencyService currencyService;


    private final UserRepo userRepo;


    @Autowired
    public WalletService(WalletRepo walletRepo, CurrencyService currencyService,
                         UserRepo userRepo) {
        this.walletRepo = walletRepo;
        this.currencyService = currencyService;
        this.userRepo = userRepo;
    }


//    public CurrencyDTO addCurrencyToWallet(CurrencyDTO currencyDTO, Long walletId) {
//        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        Currency currency = CurrencyMapper.mapToCurrency(currencyDTO);
////        currency.setWallet(wallet);
//        currencyService.save(currency);
//        return CurrencyMapper.mapToDTO(currency);
//    }

    public WalletDTO save(WalletDTO walletDTO) {
        walletRepo.save(WalletMapper.mapToWallet(walletDTO));
        return walletDTO;
    }

    public List<WalletDTO> getWalletList() {
        List<Wallet> walletList = walletRepo.findAll();
        ArrayList<WalletDTO> walletDTOList = new ArrayList<>();
        for (Wallet wallet : walletList) {
            WalletDTO walletDTO = WalletMapper.mapToDTO(wallet);
            walletDTOList.add(walletDTO);
        }
        return walletDTOList;
    }

    public void deleteWallet(Long idWallet, User userAuth) {

        User user = userRepo.findByLogin(userAuth.getLogin());
        Wallet wallet = user.getWalletList().stream()
                .filter(w -> w.getIdWallet().equals(idWallet))
                .findFirst()
                .orElse(null);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet with id " + idWallet + " not found for user with id " + userAuth.getLogin());
        }
        boolean removed = user.getWalletList().remove(wallet);
        if(removed){
            walletRepo.delete(wallet);
        }}

//    public List<CurrencyDTO> getCurrenciesByWallet(Long idWallet) {
//        List<Currency> currencyList = walletRepo.findById(idWallet)
//                .map(Wallet::getCurrency)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        ArrayList<CurrencyDTO> currencyDTOList = new ArrayList<>();
//        for (Currency currency : currencyList) {
//            CurrencyDTO currencyDTO = CurrencyMapper.mapToDTO(currency);
//            currencyDTOList.add(currencyDTO);
//        }
//        return currencyDTOList;
//    }

    public WalletDTO getWallet(Long walletId) {
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return WalletMapper.mapToDTO(wallet);
    }

    public Wallet save(Wallet wallet){
        return walletRepo.save(wallet);
    }
//    public CryptoCurrencyDTO addCryptoCurrencyToWallet(CryptoCurrencyDTO cryptoCurrencyDTO, Long walletId) {
//        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        CryptoCurrency cryptoCurrency = CryptoCurrencyMapper.mapToCurrency(cryptoCurrencyDTO);
//        cryptoCurrency.setWallet(wallet);
//        cryptoCurrencyService.saveEntity(cryptoCurrency);
//        return CryptoCurrencyMapper.mapToDTO(cryptoCurrency);
//    }

}
