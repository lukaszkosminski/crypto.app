package com.cryptoapp.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    private final CurrencyRepo currencyRepo;

    @Autowired
    public CurrencyService(CurrencyRepo currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    public Currency initUsdCurrency(){
        Currency usdCurrency = new Currency();
        usdCurrency.setName("Dollar");
        usdCurrency.setQuantity(0);
        usdCurrency.setSymbol("USD");
        return usdCurrency;
    }
    public Currency initEuroCurrency() {
        Currency plnCurrency = new Currency();
        plnCurrency.setName("Euro");
        plnCurrency.setQuantity(0);
        plnCurrency.setSymbol("EUR");
        return plnCurrency;
    }
    public Currency initPlnCurrency() {
        Currency plnCurrency = new Currency();
        plnCurrency.setName("Zloty");
        plnCurrency.setQuantity(0);
        plnCurrency.setSymbol("PLN");
        return plnCurrency;

    }
    public Currency initGbpCurrency() {
        Currency gbpCurrency = new Currency();
        gbpCurrency.setName("Funt");
        gbpCurrency.setQuantity(0);
        gbpCurrency.setSymbol("GBP");
        return gbpCurrency;

    }
    public Currency addQuantity(Long id, Integer add){
        Currency currency = currencyRepo.findById(id).orElseThrow();
        currency.setQuantity((currency.getQuantity()+add));
        currencyRepo.save(currency);
        return currency;
    }
    public Currency substractQuantity(Long id, Integer substract){
        Currency currency = currencyRepo.findById(id).orElseThrow();
        currency.setQuantity(currency.getQuantity()-substract);
        if(currency.getQuantity()<0){
            currency.setQuantity(0);
        }
        currencyRepo.save(currency);
        return currency;
    }
    public Currency setZeroQuantity(Long id){
        Currency currency = currencyRepo.findById(id).orElseThrow();
        currency.setQuantity(0);
        currencyRepo.save(currency);
        return currency;
    }
    public Currency setSpecificValueQuantity(Long id, Integer specificValueQuantity){
        Currency currency = currencyRepo.findById(id).orElseThrow();
        currency.setQuantity(specificValueQuantity);
        currencyRepo.save(currency);
        return currency;
    }

}
