package com.cryptoapp.service;

import com.cryptoapp.dto.PortfolioDTO;
import com.cryptoapp.dto.mapper.PortfolioMapper;
import com.cryptoapp.model.*;
import com.cryptoapp.repository.PortfolioRepo;
import com.cryptoapp.repository.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PortfolioService {

    private UserService userService;
    private CurrencyService currencyService;
    private CryptoCurrencyService cryptoCurrencyService;
    private CurrencyRateService currencyRateService;
    private CryptoCurrencyRateService cryptoCurrencyRateService;
    private UserRepo userRepo;
    private PortfolioRepo portfolioRepo;

    @Value("${api.url2}}")
    private String apiUrl;

@Autowired
    public PortfolioService(UserService userService, CurrencyService currencyService, CryptoCurrencyService cryptoCurrencyService, CurrencyRateService currencyRateService, CryptoCurrencyRateService cryptoCurrencyRateService, UserRepo userRepo, PortfolioRepo portfolioRepo) {
        this.userService = userService;
        this.currencyService = currencyService;
        this.cryptoCurrencyService = cryptoCurrencyService;
        this.currencyRateService = currencyRateService;
        this.cryptoCurrencyRateService = cryptoCurrencyRateService;
        this.userRepo = userRepo;
    this.portfolioRepo = portfolioRepo;
}

    public PortfolioDTO calculateTotalAssetBalance(Long idUser) throws JsonProcessingException {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setTotalAssetBalance(BigDecimal.valueOf(0));
        portfolioDTO.setCryptoCurrencyAssetBalance(BigDecimal.valueOf(0));
        portfolioDTO.setCurrencyAssetBalance(BigDecimal.valueOf(0));
        User user=userRepo.findById(idUser).orElseThrow();
        List<Wallet> wallet = user.getWallet();

        for(Wallet currentWallet:wallet){


            List<CryptoCurrency> cryptoCurrency = currentWallet.getCryptoCurrency();
            for(CryptoCurrency currentCryptoCurrency:cryptoCurrency){
//                BigDecimal price = cryptoCurrencyRateService.getPrice(currentCryptoCurrency.getSymbol());
                BigDecimal price = calculateAssetValueInPreferredCurrency(currentCryptoCurrency.getSymbol(), user.getPreferredCurrency());
                BigDecimal cryptoBalance = price.multiply(currentCryptoCurrency.getQuantity());
                portfolioDTO.setCryptoCurrencyAssetBalance(portfolioDTO.getCryptoCurrencyAssetBalance().add(cryptoBalance));
                portfolioDTO.setTotalAssetBalance(portfolioDTO.getTotalAssetBalance().add(cryptoBalance));
            }
            List<Currency> currency = currentWallet.getCurrency();
            for(Currency currentCurrency:currency){
//                BigDecimal price = currencyRateService.getPrice(currentCurrency.getSymbol());
                BigDecimal price = calculateAssetValueInPreferredCurrency(currentCurrency.getSymbol(), user.getPreferredCurrency());
                BigDecimal currencyBalance = price.multiply(currentCurrency.getQuantity());
                portfolioDTO.setCurrencyAssetBalance(portfolioDTO.getCurrencyAssetBalance().add(currencyBalance));
                portfolioDTO.setTotalAssetBalance(portfolioDTO.getTotalAssetBalance().add(currencyBalance));
            }


        }
        portfolioRepo.save(PortfolioMapper.mapToPortfolio(portfolioDTO));

        return portfolioDTO;

    }
    public BigDecimal calculateAssetValueInPreferredCurrency(String symbol, String preferredCurrency) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(String.format(apiUrl, symbol,preferredCurrency), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        CurrencyAssetBalance currencyAssetBalance = objectMapper.readValue(jsonResponse, CurrencyAssetBalance.class);
        return currencyAssetBalance.getCurrencyBalance().get(symbol);
    }

}
