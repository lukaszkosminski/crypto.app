package com.cryptoapp.controller;

import com.cryptoapp.dto.CurrencyDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallet")
    public WalletDTO createWallet(@RequestBody WalletDTO walletDTO) {
        return walletService.save(walletDTO);
    }

    @GetMapping("/wallet/{idWallet}/currencies")
    public List<CurrencyDTO> getCurrenciesByWallet(@PathVariable Long idWallet) {
        return walletService.getCurrenciesByWallet(idWallet);
    }

    @PutMapping("wallet/{walletId}/currency")
    public CurrencyDTO addCurrencyToWallet(@RequestBody CurrencyDTO currencyDTO, @PathVariable Long walletId) {
        return walletService.addCurrencyToWallet(currencyDTO, walletId);
    }

    @DeleteMapping("/wallet/{idWallet}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWallet(@PathVariable Long idWallet) {
        walletService.deleteWallet(idWallet);
    }

}
