package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.CryptoCurrencyRateDTO;
import com.cryptoapp.dto.CurrencyRateDTO;
import com.cryptoapp.model.CryptoCurrencyRate;
import com.cryptoapp.model.CurrencyRate;

public class CurrencyRateMapper {

    public static CurrencyRate mapToCurrency(CurrencyRateDTO currencyRateDTO) {
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCurrency(currencyRateDTO.getCurrency());
        currencyRate.setPrice(currencyRateDTO.getPrice());
        return currencyRate;
    }
}
