package com.cryptoapp.controllers;

import com.cryptoapp.entities.WalletFiatEntity;
import com.cryptoapp.repositories.WalletFiatRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class WalletFiatController {

    private WalletFiatRepository walletFiatRepository;
    @Autowired
    public WalletFiatController(WalletFiatRepository walletFiatRepository) {
        this.walletFiatRepository = walletFiatRepository;
    }

    @PostConstruct
    public void createWalletsInDbForTest(){
        WalletFiatEntity walletFiat = new WalletFiatEntity();
        walletFiat.setEuro(10);
        walletFiat.setPln(10);
        walletFiat.setGpb(10);
        walletFiat.setUsd(10);
        walletFiatRepository.save(walletFiat);
        WalletFiatEntity walletFiat1 = new WalletFiatEntity();
        walletFiat1.setEuro(20);
        walletFiat1.setPln(20);
        walletFiat1.setGpb(20);
        walletFiat1.setUsd(20);
        walletFiatRepository.save(walletFiat1);
        WalletFiatEntity walletFiat2 = new WalletFiatEntity();
        walletFiat2.setEuro(30);
        walletFiat2.setPln(30);
        walletFiat2.setGpb(30);
        walletFiat2.setUsd(30);
        walletFiatRepository.save(walletFiat2);
    }
    @GetMapping("/test")
    public String test(){
        return "test";
    }
    @GetMapping("/wallet-fiat")
    public List<WalletFiatEntity> getWallets(){
        return walletFiatRepository.findAll();
    }
    @GetMapping("/wallet-fiat/{id}")
    public WalletFiatEntity getWalletFiatById(@PathVariable Long id){
        return walletFiatRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/wallet-fiat/{id}")
    public WalletFiatEntity updateWalletFiat(@PathVariable Long id, @RequestBody WalletFiatEntity walletFiat){
        WalletFiatEntity updatedWallet = walletFiatRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        updatedWallet.setUsd(walletFiat.getUsd());
        updatedWallet.setPln(walletFiat.getPln());
        updatedWallet.setEuro(walletFiat.getEuro());
        updatedWallet.setGpb(walletFiat.getGpb());
        return walletFiatRepository.save(updatedWallet);
    }
    @DeleteMapping("/wallet-fiat/{id}")
    public void deleteWalletFiat(@PathVariable Long id){
        walletFiatRepository.deleteById(id);
    }
    @PostMapping("/wallet-fiat")
    public WalletFiatEntity createWalletFiat(@RequestBody WalletFiatEntity walletFiat){
        return walletFiatRepository.save(walletFiat);
    }

}
