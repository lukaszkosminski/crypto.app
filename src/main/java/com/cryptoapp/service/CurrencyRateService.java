package com.cryptoapp.service;

import com.cryptoapp.dto.CryptoCurrencyRateDTO;
import com.cryptoapp.dto.CurrencyRateDTO;
import com.cryptoapp.dto.mapper.CryptoCurrencyRateMapper;
import com.cryptoapp.dto.mapper.CurrencyRateMapper;
import com.cryptoapp.model.CryptoCurrency;
import com.cryptoapp.model.Currency;
import com.cryptoapp.model.USDCurrencyRate;
import com.cryptoapp.repository.CryptoCurrencyRateRepository;
import com.cryptoapp.repository.CryptoCurrencyRepository;
import com.cryptoapp.repository.CurrencyRateRepo;
import com.cryptoapp.repository.CurrencyRepo;
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
public class CurrencyRateService {


    private final CurrencyRateRepo currencyRateRepo;

    private final CurrencyRepo currencyRepo;

    @Value("${api.url}}")
    private String apiUrl;

    @Autowired
    public CurrencyRateService(CurrencyRateRepo currencyRateRepo, CurrencyRepo currencyRepo) {
        this.currencyRateRepo = currencyRateRepo;
        this.currencyRepo = currencyRepo;
    }


    public BigDecimal getPrice(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(String.format(apiUrl, symbol), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        USDCurrencyRate usdCurrencyRate = objectMapper.readValue(jsonResponse, USDCurrencyRate.class);
        return usdCurrencyRate.getUSD();
    }

    public List<CurrencyRateDTO> createCurrencyRateDTOList() throws JsonProcessingException {
        List<Currency> allCurrency = currencyRepo.findAll();
        List<CurrencyRateDTO> currencyRateDTOList = new ArrayList<>();

        for (Currency currency : allCurrency) {
            CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO(currency, getPrice(currency.getSymbol()));
            currencyRateDTOList.add(currencyRateDTO);
        }

        return currencyRateDTOList;
    }

    @Scheduled(fixedDelay = 20000)
    public void saveListToDb() throws JsonProcessingException {
        List<CurrencyRateDTO> currencyRateDTOList = createCurrencyRateDTOList();
        for (CurrencyRateDTO currencyRateDTO : currencyRateDTOList) {
            currencyRateRepo.save(CurrencyRateMapper.mapToCurrency(currencyRateDTO));
        }
    }
}
