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

    @PostMapping("/currency/create")
    public CurrencyDTO createCurrency(@RequestBody CurrencyDTO currencyDTO) {
        return currencyService.mapAndSave(currencyDTO);
    }

    @PutMapping("/currency/{idCurrency}/add")
    public CurrencyDTO addQuantity(@PathVariable Long idCurrency, @RequestParam Integer quantity) {
        return currencyService.addQuantity(idCurrency, quantity);
    }

    @PutMapping("/currency/{idCurrency}/subtract")
    public CurrencyDTO subtractQuantity(@PathVariable Long idCurrency, @RequestParam Integer quantity) throws Exception {
        return currencyService.subtractQuantity(idCurrency, quantity);
    }

    @PutMapping("/currency/{idCurrency}/zero")
    public CurrencyDTO zeroQuantity(@PathVariable Long idCurrency) {
        return currencyService.setZeroQuantity(idCurrency);
    }

    @PutMapping("/currency/{idCurrency}/set-specific-value")
    public CurrencyDTO setSpecificValue(@PathVariable Long idCurrency, @RequestParam Integer quantity) {
        return currencyService.setSpecificValueQuantity(idCurrency, quantity);
    }

    @GetMapping("/currency")
    public List<CurrencyDTO> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/currency/{idCurrency}")
    public CurrencyDTO getCurrencyById(@PathVariable Long idCurrency) {
        return currencyService.getCurrencyById(idCurrency);
    }

    @DeleteMapping("currency/{idCurrency}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrency(@PathVariable Long idCurrency) {
        currencyService.deleteCurrency(idCurrency);
    }

}
