package com.cryptoapp.currency;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Api(value = "Controller Currency")
public class CurrencyController {

    private final CurrencyRepo currencyRepo;
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyRepo currencyRepo, CurrencyService currencyService) {
        this.currencyRepo = currencyRepo;
        this.currencyService = currencyService;
    }

    @Transactional
    @PostMapping("/init-currency")
    @ApiOperation(value = "initCurrencyDb")
    public String initCurrencyDb() {
        currencyRepo.save(currencyService.initUsdCurrency());
        currencyRepo.save(currencyService.initEuroCurrency());
        currencyRepo.save(currencyService.initPlnCurrency());
        currencyRepo.save(currencyService.initGbpCurrency());
        return "Currency USD, EURO, PLN, GBP are created";
    }

    @PutMapping("/currency/{idCurrency}/add")
    @ApiOperation(value = "addQuantity")
    public Currency addQuantity(@PathVariable Long idCurrency, @RequestParam Integer quantity) {
        return currencyService.addQuantity(idCurrency, quantity);
    }

    @PutMapping("/currency/{idCurrency}/substract")
    @ApiOperation(value = "substractQuantity")
    public Currency substractQuantity(@PathVariable Long idCurrency, @RequestParam Integer quantity) {
        return currencyService.substractQuantity(idCurrency, quantity);
    }

    @PutMapping("/currency/{idCurrency}/zero")
    @ApiOperation(value = "zeroQuantity")
    public Currency zeroQuantity(@PathVariable Long idCurrency) {
        return currencyService.setZeroQuantity(idCurrency);
    }

    @PutMapping("/currency/{idCurrency}/set-specific-value")
    @ApiOperation(value = "setSpecificValue")
    public Currency setSpecificValue(@PathVariable Long idCurrency, @RequestParam Integer quantity) {
        return currencyService.setSpecificValueQuantity(idCurrency, quantity);
    }

    @GetMapping("/currency")
    @ApiOperation(value = "getAllCurrencies")
    public List<Currency> getAllCurrencies() {
        return currencyRepo.findAll();
    }

    @GetMapping("/currency/{idCurrency}")
    @ApiOperation(value = "getCurrencyById")
    public Currency getCurrencyById(@PathVariable Long idCurrency) {
        return currencyRepo.findById(idCurrency).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("currency/{idCurrency}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "deleteCurrency")
    public void deleteCurrency(@PathVariable Long idCurrency) {
        currencyRepo.deleteById(idCurrency);
    }


}
