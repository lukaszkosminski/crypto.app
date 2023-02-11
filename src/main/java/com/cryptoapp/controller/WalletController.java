package com.cryptoapp.controller;

import com.cryptoapp.dto.CurrencyDTO;
import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.service.CurrencyService;
import com.cryptoapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WalletController {
    private WalletService walletService;
    private CurrencyService currencyService;

    @Autowired
    public WalletController(WalletService walletService, CurrencyService currencyService) {
        this.walletService = walletService;
        this.currencyService = currencyService;
    }

    @GetMapping("/wallet")
    public List<WalletDTO> getWalletList() {
        return walletService.getWalletList();
    }

    @PostMapping("/wallet")
    public WalletDTO createWallet(@RequestBody WalletDTO walletDTO) {
        return walletService.mapAndSave(walletDTO);
    }

    @GetMapping("/wallet/{idWallet}/currencies")
    public List<CurrencyDTO> getCurrenciesByWallet(@PathVariable Long idWallet) {
        return walletService.getCurrenciesByWallet(idWallet);
    }

    @PutMapping("wallet/{walletId}/currency/{currencyId}")
    public CurrencyDTO addCurrencyToWallet(@PathVariable Long currencyId, @PathVariable Long walletId) {
        return walletService.addCurrencyToWallet(currencyId, walletId);
    }

    @DeleteMapping("/wallet/{idWallet}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWallet(@PathVariable Long idWallet) {
        walletService.deleteWallet(idWallet);
    }

}
