package com.cryptoapp.service;

import com.cryptoapp.dto.CurrencyDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.dto.mapper.WalletMapper;
import com.cryptoapp.model.Currency;
import com.cryptoapp.model.Wallet;
import com.cryptoapp.repository.CurrencyRepo;
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
    private final CurrencyRepo currencyRepo;
    private final CurrencyService currencyService;

    @Autowired
    public WalletService(WalletRepo walletRepo, CurrencyRepo currencyRepo, CurrencyService currencyService) {
        this.walletRepo = walletRepo;
        this.currencyRepo = currencyRepo;
        this.currencyService = currencyService;
    }

    public CurrencyDTO addCurrencyToWallet(Long currencyId, Long walletId) {
        Currency currency = currencyRepo.findById(currencyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        currency.setWallet(wallet);
        currencyRepo.save(currency);
        return currencyService.mapToDTO(currency);
    }

    public Wallet mapToWallet(WalletDTO walletDTO) {
        return WalletMapper.mapToWallet(walletDTO);
    }

    public WalletDTO mapToDTO(Wallet wallet) {
        return WalletMapper.mapToDTO(wallet);
    }

    public WalletDTO mapAndSave(WalletDTO walletDTO) {
        walletRepo.save(mapToWallet(walletDTO));
        return walletDTO;
    }

    public List<WalletDTO> getWalletList() {
        List<Wallet> walletList = walletRepo.findAll();
        ArrayList<WalletDTO> walletDTOList = new ArrayList<>();
        for (Wallet wallet : walletList) {
            WalletDTO walletDTO = mapToDTO(wallet);
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
            CurrencyDTO currencyDTO = currencyService.mapToDTO(currency);
            currencyDTOList.add(currencyDTO);
        }
        return currencyDTOList;
    }
}
