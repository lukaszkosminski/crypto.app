package com.cryptoapp.service;

import com.cryptoapp.dto.CurrencyRateDTO;
import com.cryptoapp.dto.mapper.CurrencyRateMapper;
import com.cryptoapp.model.Currency;
import com.cryptoapp.repository.CurrencyRateRepo;
import com.cryptoapp.repository.CurrencyRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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

    @Value("${api.url2}}")
    private String apiUrl;

    private String sellSymbol = "PLN";

    @Autowired
    public CurrencyRateService(CurrencyRateRepo currencyRateRepo, CurrencyRepo currencyRepo) {
        this.currencyRateRepo = currencyRateRepo;
        this.currencyRepo = currencyRepo;
    }


    public BigDecimal getPrice(String symbolBuy, String symbolSell) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(String.format(apiUrl, symbolBuy,symbolSell), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        BigDecimal value = rootNode.get(symbolSell).decimalValue();
        return value;
//        return currencyAssetBalance.getCurrencyBalance().get(symbol);    }
    }
    public List<CurrencyRateDTO> createCurrencyRateDTOList(String sellSymbol) throws JsonProcessingException {
        List<Currency> allCurrency = currencyRepo.findAll();
        List<CurrencyRateDTO> currencyRateDTOList = new ArrayList<>();

        for (Currency currency : allCurrency) {
            CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO(currency, getPrice(currency.getSymbol(), sellSymbol),sellSymbol);
            currencyRateDTOList.add(currencyRateDTO);
        }

        return currencyRateDTOList;
    }

    @Scheduled(fixedDelay = 20000)
    public void saveListToDb() throws JsonProcessingException {
        List<CurrencyRateDTO> currencyRateDTOList = createCurrencyRateDTOList(sellSymbol);
        for (CurrencyRateDTO currencyRateDTO : currencyRateDTOList) {
            currencyRateRepo.save(CurrencyRateMapper.mapToCurrency(currencyRateDTO));
        }
    }
}
