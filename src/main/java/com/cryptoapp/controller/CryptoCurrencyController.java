package com.cryptoapp.controller;

import com.cryptoapp.dto.CryptoCurrencyDTO;
import com.cryptoapp.dto.mapper.CryptoCurrencyMapper;
import com.cryptoapp.repository.CryptoCurrencyRepository;
import com.cryptoapp.service.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoCurrencyController {

    private final CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    public CryptoCurrencyController(CryptoCurrencyService cryptoCurrencyService) {
        this.cryptoCurrencyService = cryptoCurrencyService;
    }

    @PostMapping("/crypto/create")
    public void create(@RequestBody CryptoCurrencyDTO cryptoCurrencyDTO) {
       cryptoCurrencyService.save(cryptoCurrencyDTO);
    }

}
