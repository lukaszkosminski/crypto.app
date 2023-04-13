package com.cryptoapp.service;

import com.cryptoapp.model.CurrencySymbol;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class CryptoSymbolChecker {

    @Value("${api.listCrypto}")
    private String url;


    public Boolean checkIsCrypto(String symbol) {


        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        Boolean isCrypto;
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.has("Data")) {
            JSONObject data = jsonObject.getJSONObject("Data");
            if (data.has(symbol)) {
                return true;
            }
        }
        try {
            CurrencySymbol.valueOf(symbol);
            return false;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Symbol not found");
        }
    }
}