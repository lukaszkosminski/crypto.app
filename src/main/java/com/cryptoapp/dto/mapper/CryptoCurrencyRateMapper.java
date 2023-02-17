package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.CryptoCurrencyRateDTO;
import com.cryptoapp.model.CryptoCurrencyRate;


public class CryptoCurrencyRateMapper {

    public static CryptoCurrencyRate mapToCurrency(CryptoCurrencyRateDTO cryptoCurrencyRateDTO) {
        CryptoCurrencyRate cryptoCurrencyRate = new CryptoCurrencyRate();
        cryptoCurrencyRate.setCryptoCurrency(cryptoCurrencyRateDTO.getCryptoCurrency());
        cryptoCurrencyRate.setPrice(cryptoCurrencyRateDTO.getPrice());
        return cryptoCurrencyRate;
    }

}
