package com.cryptoapp.service;

import com.cryptoapp.dto.CryptoCurrencyRateDTO;
import com.cryptoapp.dto.mapper.CryptoCurrencyRateMapper;
import com.cryptoapp.model.CryptoCurrency;
import com.cryptoapp.model.CurrencyAssetBalance;
import com.cryptoapp.repository.CryptoCurrencyRateRepository;
import com.cryptoapp.repository.CryptoCurrencyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoCurrencyRateService {

    private final CryptoCurrencyRateRepository cryptoCurrencyRateRepository;

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    @Value("${api.url}}")
    private String apiUrl;

    @Autowired
    public CryptoCurrencyRateService(CryptoCurrencyRateRepository cryptoCurrencyRateRepository,
                                     CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRateRepository = cryptoCurrencyRateRepository;
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }

    public BigDecimal getPrice(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(String.format(apiUrl, symbol), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        CurrencyAssetBalance currencyAssetBalance = objectMapper.readValue(jsonResponse, CurrencyAssetBalance.class);
        return currencyAssetBalance.getCurrencyBalance().get(symbol);    }

    public List<CryptoCurrencyRateDTO> createCryptoCurrencyRateDTOList() throws JsonProcessingException {
        List<CryptoCurrency> allCrypto = cryptoCurrencyRepository.findAll();
        List<CryptoCurrencyRateDTO> cryptoCurrencyRateDTOList = new ArrayList<>();

        for (CryptoCurrency cryptoCurrency : allCrypto) {
            CryptoCurrencyRateDTO cryptoCurrencyRateDTO = new CryptoCurrencyRateDTO(getPrice(cryptoCurrency.getSymbol()), cryptoCurrency);
            cryptoCurrencyRateDTOList.add(cryptoCurrencyRateDTO);
        }

        return cryptoCurrencyRateDTOList;
    }

    @Scheduled(fixedDelay = 20000)
    public void saveListToDb() throws JsonProcessingException {
        List<CryptoCurrencyRateDTO> cryptoCurrencyRateDTOList = createCryptoCurrencyRateDTOList();
        for (CryptoCurrencyRateDTO cryptoCurrencyRateDTO : cryptoCurrencyRateDTOList) {
            cryptoCurrencyRateRepository.save(CryptoCurrencyRateMapper.mapToCurrency(cryptoCurrencyRateDTO));
        }
    }

}
