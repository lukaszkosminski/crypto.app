package com.cryptoapp.controller;

import com.cryptoapp.dto.CurrencyDTO;
import com.cryptoapp.service.CurrencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Controller Currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/currency/create")
    @ApiOperation(value = "createCurrency")
    public CurrencyDTO createCurrency(@RequestBody CurrencyDTO currencyDTO) {
        return currencyService.mapAndSave(currencyDTO);
    }

    @PutMapping("/currency/{idCurrency}/add")
    @ApiOperation(value = "addQuantity")
    public CurrencyDTO addQuantity(@PathVariable Long idCurrency, @RequestParam Integer quantity) {
        return currencyService.addQuantity(idCurrency, quantity);
    }

    @PutMapping("/currency/{idCurrency}/subtract")
    @ApiOperation(value = "subtractQuantity")
    public CurrencyDTO subtractQuantity(@PathVariable Long idCurrency, @RequestParam Integer quantity) throws Exception {
        return currencyService.subtractQuantity(idCurrency, quantity);
    }

    @PutMapping("/currency/{idCurrency}/zero")
    @ApiOperation(value = "zeroQuantity")
    public CurrencyDTO zeroQuantity(@PathVariable Long idCurrency) {
        return currencyService.setZeroQuantity(idCurrency);
    }

    @PutMapping("/currency/{idCurrency}/set-specific-value")
    @ApiOperation(value = "setSpecificValue")
    public CurrencyDTO setSpecificValue(@PathVariable Long idCurrency, @RequestParam Integer quantity) {
        return currencyService.setSpecificValueQuantity(idCurrency, quantity);
    }

    @GetMapping("/currency")
    @ApiOperation(value = "getAllCurrencies")
    public List<CurrencyDTO> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/currency/{idCurrency}")
    @ApiOperation(value = "getCurrencyById")
    public CurrencyDTO getCurrencyById(@PathVariable Long idCurrency) {
        return currencyService.getCurrencyById(idCurrency);
    }

    @DeleteMapping("currency/{idCurrency}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "deleteCurrency")
    public void deleteCurrency(@PathVariable Long idCurrency) {
        currencyService.deleteCurrency(idCurrency);
    }

}
