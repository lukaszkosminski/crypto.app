package com.cryptoapp.controller;

import com.cryptoapp.dto.CryptoCurrencyDTO;
import com.cryptoapp.dto.mapper.CryptoCurrencyMapper;
import com.cryptoapp.repository.CryptoCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoCurrencyController {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    @Autowired
    public CryptoCurrencyController(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }

    @PostMapping("/crypto/create")
    public void create(@RequestBody CryptoCurrencyDTO cryptoCurrencyDTO) {
        cryptoCurrencyRepository.save(CryptoCurrencyMapper.mapToCurrency(cryptoCurrencyDTO));
    }
}
