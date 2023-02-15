package com.cryptoapp.service;

import com.cryptoapp.model.CryptoCurrency;
import com.cryptoapp.model.crypto.Ada;
import com.cryptoapp.model.crypto.Btc;
import com.cryptoapp.model.crypto.Eth;
import com.cryptoapp.model.crypto.USDCurrencyRate;
import com.cryptoapp.repository.CryptoCurrencyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoCurrencyService {

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>();

    @Autowired
    public CryptoCurrencyService(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }


    public BigDecimal getprice(String symbol) throws JsonProcessingException {
        String API_URL = String.format("https://min-api.cryptocompare.com/data/price?fsym=%s&tsyms=USD&api_key=57bf7cc844e4afd9d7ab7d87b7def9366f9208aa26f9d5d699662586b360cab1", symbol);

        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(API_URL, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        USDCurrencyRate usdCurrencyRate = objectMapper.readValue(jsonResponse, USDCurrencyRate.class);
        return usdCurrencyRate.getUSD();

    }

    public List<CryptoCurrency> prepareListCryptoCurrency() throws JsonProcessingException {
        CryptoCurrency btc = new Btc(getprice("BTC"));
        CryptoCurrency ada = new Ada(getprice("ADA"));
        CryptoCurrency eth = new Eth(getprice("ETH"));
        cryptoCurrencyList.add(btc);
        cryptoCurrencyList.add(ada);
        cryptoCurrencyList.add(eth);

        return cryptoCurrencyList;
    }

    public void saveListToDb(List<CryptoCurrency> cryptoCurrencyList) {
        for (CryptoCurrency cryptoCurrency : cryptoCurrencyList) {
            cryptoCurrencyRepository.save(cryptoCurrency);
        }
    }

    @Scheduled(fixedDelay = 20000)
    public void initShedule() throws JsonProcessingException {
        saveListToDb(prepareListCryptoCurrency());
    }


}
