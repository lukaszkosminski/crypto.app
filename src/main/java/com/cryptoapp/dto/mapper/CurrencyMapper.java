package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.CurrencyDTO;
import com.cryptoapp.model.Currency;

public class CurrencyMapper {
    public static Currency mapToCurrency(CurrencyDTO currencyDTO) {
        Currency currency = new Currency();
        currency.setName(currencyDTO.getName());
        currency.setQuantity(currencyDTO.getQuantity());
        currency.setSymbol(currencyDTO.getSymbol());
        currency.setWallet(currencyDTO.getWallet());
        return currency;
    }

    public static CurrencyDTO mapToDTO(Currency currency) {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setName(currency.getName());
        currencyDTO.setQuantity(currency.getQuantity());
        currencyDTO.setSymbol(currency.getSymbol());
        currencyDTO.setWallet(currency.getWallet());
        return currencyDTO;
    }

}
