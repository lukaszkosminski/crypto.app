package com.cryptoapp.controller;

import com.cryptoapp.dto.CurrencyDTO;
import com.cryptoapp.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("api/user/currency/create")
    public CurrencyDTO createCurrency(@RequestBody CurrencyDTO currencyDTO) {
        return currencyService.save(currencyDTO);
    }

    @GetMapping("api/user/currency")
    public List<CurrencyDTO> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("api/user/currency/{idCurrency}")
    public CurrencyDTO getCurrencyById(@PathVariable Long idCurrency) {
        return currencyService.getCurrencyById(idCurrency);
    }

    @DeleteMapping("api/user/currency/{idCurrency}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrency(@PathVariable Long idCurrency) {
        currencyService.deleteCurrency(idCurrency);
    }

}
