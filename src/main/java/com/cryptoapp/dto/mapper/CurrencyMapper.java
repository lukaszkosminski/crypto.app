package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.CurrencyDTO;
import com.cryptoapp.model.Currency;

public class CurrencyMapper {

    public static Currency mapToCurrency(CurrencyDTO currencyDTO) {
        Currency currency = new Currency();
        currency.setSymbol(currencyDTO.getSymbol());
        currency.setIsCrypto(currencyDTO.getIsCrypto());
        return currency;
    }

    public static CurrencyDTO mapToDTO(Currency currency) {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setSymbol(currency.getSymbol());
        currencyDTO.setIsCrypto(currency.getIsCrypto());
        return currencyDTO;
    }

}
