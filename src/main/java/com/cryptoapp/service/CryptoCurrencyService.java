package com.cryptoapp.service;

import com.cryptoapp.dto.CryptoCurrencyDTO;
import com.cryptoapp.dto.mapper.CryptoCurrencyMapper;
import com.cryptoapp.repository.CryptoCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoCurrencyService {

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    private final CryptoCurrencyRateService cryptoCurrencyRateService;

    @Autowired
    public CryptoCurrencyService(CryptoCurrencyRepository cryptoCurrencyRepository, CryptoCurrencyRateService cryptoCurrencyRateService) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.cryptoCurrencyRateService = cryptoCurrencyRateService;
    }

    public CryptoCurrencyDTO save(CryptoCurrencyDTO cryptoCurrencyDTO){
        cryptoCurrencyRepository.save(CryptoCurrencyMapper.mapToCurrency(cryptoCurrencyDTO));
        return cryptoCurrencyDTO;
    }

}
