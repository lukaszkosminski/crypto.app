package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.CryptoCurrencyDTO;
import com.cryptoapp.model.CryptoCurrency;


public class CryptoCurrencyMapper {

    public static CryptoCurrency mapToCurrency(CryptoCurrencyDTO cryptoCurrencyDTO) {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setName(cryptoCurrencyDTO.getName());
        cryptoCurrency.setPrice(cryptoCurrencyDTO.getPrice());
        cryptoCurrency.setSymbol(cryptoCurrencyDTO.getSymbol());
        return cryptoCurrency;
    }

    public static CryptoCurrencyDTO mapToDTO(CryptoCurrency cryptoCurrency) {
        CryptoCurrencyDTO cryptoCurrencyDTO = new CryptoCurrencyDTO();
        cryptoCurrencyDTO.setName(cryptoCurrency.getName());
        cryptoCurrencyDTO.setPrice(cryptoCurrency.getPrice());
        cryptoCurrencyDTO.setSymbol(cryptoCurrency.getSymbol());
        return cryptoCurrencyDTO;
    }

}
