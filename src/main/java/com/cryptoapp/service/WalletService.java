package com.cryptoapp.service;

import com.cryptoapp.dto.CryptoCurrencyDTO;
import com.cryptoapp.dto.CurrencyDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.dto.mapper.CryptoCurrencyMapper;
import com.cryptoapp.dto.mapper.CurrencyMapper;
import com.cryptoapp.dto.mapper.WalletMapper;
import com.cryptoapp.model.CryptoCurrency;
import com.cryptoapp.model.Currency;
import com.cryptoapp.model.Wallet;
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
    private final CryptoCurrencyService cryptoCurrencyService;


    @Autowired
    public WalletService(WalletRepo walletRepo, CurrencyService currencyService, CryptoCurrencyService cryptoCurrencyService) {
        this.walletRepo = walletRepo;
        this.currencyService = currencyService;
        this.cryptoCurrencyService = cryptoCurrencyService;
    }

    public CurrencyDTO addCurrencyToWallet(CurrencyDTO currencyDTO, Long walletId) {
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Currency currency = CurrencyMapper.mapToCurrency(currencyDTO);
        currency.setWallet(wallet);
        currencyService.save(currency);
        return CurrencyMapper.mapToDTO(currency);
    }

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

    public void deleteWallet(Long idWallet) {
        walletRepo.deleteById(idWallet);
    }

    public List<CurrencyDTO> getCurrenciesByWallet(Long idWallet) {
        List<Currency> currencyList = walletRepo.findById(idWallet)
                .map(Wallet::getCurrency)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ArrayList<CurrencyDTO> currencyDTOList = new ArrayList<>();
        for (Currency currency : currencyList) {
            CurrencyDTO currencyDTO = CurrencyMapper.mapToDTO(currency);
            currencyDTOList.add(currencyDTO);
        }
        return currencyDTOList;
    }

    public WalletDTO getWallet(Long walletId) {
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return WalletMapper.mapToDTO(wallet);
    }

    public void save(Wallet wallet){
        walletRepo.save(wallet);
    }
    public CryptoCurrencyDTO addCryptoCurrencyToWallet(CryptoCurrencyDTO cryptoCurrencyDTO, Long walletId) {
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CryptoCurrency cryptoCurrency = CryptoCurrencyMapper.mapToCurrency(cryptoCurrencyDTO);
        cryptoCurrency.setWallet(wallet);
        cryptoCurrencyService.saveEntity(cryptoCurrency);
        return CryptoCurrencyMapper.mapToDTO(cryptoCurrency);
    }

}
