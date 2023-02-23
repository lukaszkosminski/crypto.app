package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.CryptoCurrencyDTO;
import com.cryptoapp.model.CryptoCurrency;

public class CryptoCurrencyMapper {

    public static CryptoCurrency mapToCurrency(CryptoCurrencyDTO cryptoCurrencyDTO) {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setSymbol(cryptoCurrencyDTO.getSymbol());
        cryptoCurrency.setName(cryptoCurrencyDTO.getName());
        cryptoCurrency.setQuantity(cryptoCurrencyDTO.getQuantity());
        return cryptoCurrency;
    }

    public static CryptoCurrencyDTO mapToDTO(CryptoCurrency cryptoCurrency) {
        CryptoCurrencyDTO cryptoCurrencyDTO = new CryptoCurrencyDTO();
        cryptoCurrencyDTO.setName(cryptoCurrency.getName());
        cryptoCurrencyDTO.setSymbol(cryptoCurrency.getSymbol());
        cryptoCurrencyDTO.setQuantity(cryptoCurrency.getQuantity());
        return cryptoCurrencyDTO;
    }

}
