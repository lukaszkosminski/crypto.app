package com.cryptoapp.service;

import com.cryptoapp.dto.CryptoCurrencyDTO;
import com.cryptoapp.dto.mapper.CryptoCurrencyMapper;
import com.cryptoapp.model.USDCurrencyRate;
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

    List<CryptoCurrencyDTO> cryptoCurrencyListDTO = new ArrayList<>();

    @Autowired
    public CryptoCurrencyService(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }


    public BigDecimal getPrice(String symbol) throws JsonProcessingException {
        String API_URL = String.format("https://min-api.cryptocompare.com/data/price?fsym=%s&tsyms=USD&api_key=57bf7cc844e4afd9d7ab7d87b7def9366f9208aa26f9d5d699662586b360cab1", symbol);

        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(API_URL, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        USDCurrencyRate usdCurrencyRate = objectMapper.readValue(jsonResponse, USDCurrencyRate.class);
        return usdCurrencyRate.getUSD();

    }

    public List<CryptoCurrencyDTO> prepareListCryptoCurrencyDTO() throws JsonProcessingException {
        CryptoCurrencyDTO btc = new CryptoCurrencyDTO();
        btc.setPrice(getPrice("BTC"));
        btc.setSymbol("BTC");
        btc.setName("Bitcoin");
        CryptoCurrencyDTO ada = new CryptoCurrencyDTO();
        ada.setPrice(getPrice("ADA"));
        ada.setName("Cardano");
        ada.setSymbol("Ada");
        CryptoCurrencyDTO eth = new CryptoCurrencyDTO();
        eth.setPrice(getPrice("ETH"));
        eth.setSymbol("ETH");
        eth.setName("Ethereum");
        cryptoCurrencyListDTO.add(btc);
        cryptoCurrencyListDTO.add(ada);
        cryptoCurrencyListDTO.add(eth);

        return cryptoCurrencyListDTO;
    }

    public void saveListToDb(List<CryptoCurrencyDTO> cryptoCurrencyListDTO) {
        for (CryptoCurrencyDTO cryptoCurrencyDTO : cryptoCurrencyListDTO) {
            cryptoCurrencyRepository.save(CryptoCurrencyMapper.mapToCurrency(cryptoCurrencyDTO));
        }
    }

    @Scheduled(fixedDelay = 20000)
    public void initShedule() throws JsonProcessingException {
        saveListToDb(prepareListCryptoCurrencyDTO());
    }


}
