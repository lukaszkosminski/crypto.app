package com.cryptoapp.wallet;

import com.cryptoapp.currency.Currency;
import com.cryptoapp.currency.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class WalletController {

    private WalletRepo walletRepo;
    private CurrencyRepo currencyRepo;
    @Autowired
    public WalletController(WalletRepo walletRepo, CurrencyRepo currencyRepo) {
        this.walletRepo = walletRepo;
        this.currencyRepo = currencyRepo;
    }

    @GetMapping("/wallet")
    public List<Wallet> getCategories() {
        return walletRepo.findAll();
    }

    @PostMapping("/wallet")
    public Wallet createWallet(@RequestBody Wallet wallet) {
        return walletRepo.save(wallet);
    }

    @GetMapping("/wallet/{idWallet}/currencies")
    public List<Currency> getCurrenciesByWallet(@PathVariable Long idWallet){
        return walletRepo.findById(idWallet)
                .map(Wallet::getCurrency)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("wallet/{walletId}/currency/{currencyId}")
    public Currency addCurrencyToWallet(@PathVariable Long currencyId, @PathVariable Long walletId) {
          Currency currency = currencyRepo.findById(currencyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        currency.setWallet(wallet);
        return currencyRepo.save(currency);
    }

    @DeleteMapping("/wallet/{idWallet}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWallet(@PathVariable Long id){
        walletRepo.deleteById(id);
    }


}
