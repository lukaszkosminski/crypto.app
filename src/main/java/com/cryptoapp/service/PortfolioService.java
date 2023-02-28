package com.cryptoapp.service;

import com.cryptoapp.dto.PortfolioDTO;
import com.cryptoapp.dto.mapper.PortfolioMapper;
import com.cryptoapp.model.CryptoCurrency;
import com.cryptoapp.model.Currency;
import com.cryptoapp.model.User;
import com.cryptoapp.model.Wallet;
import com.cryptoapp.repository.PortfolioRepo;
import com.cryptoapp.repository.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PortfolioDTO calculateTotalValue(Long idUser) throws JsonProcessingException {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setTotalAssetBalance(BigDecimal.valueOf(0));
        User user=userRepo.findById(idUser).orElseThrow();
        List<Wallet> wallet = user.getWallet();

        for(Wallet currentWallet:wallet){


            List<CryptoCurrency> cryptoCurrency = currentWallet.getCryptoCurrency();
            for(CryptoCurrency currentCryptoCurrency:cryptoCurrency){
                BigDecimal price = cryptoCurrencyRateService.getPrice(currentCryptoCurrency.getSymbol());
                BigDecimal multiply = price.multiply(currentCryptoCurrency.getQuantity());
                portfolioDTO.setTotalAssetBalance(portfolioDTO.getTotalAssetBalance().add(multiply));
            }
            List<Currency> currency = currentWallet.getCurrency();
            for(Currency currentCurrency:currency){
                BigDecimal price = currencyRateService.getPrice(currentCurrency.getSymbol());
                BigDecimal multiply = price.multiply(currentCurrency.getQuantity());
                portfolioDTO.setTotalAssetBalance(portfolioDTO.getTotalAssetBalance().add(multiply));
            }


        }
        portfolioRepo.save(PortfolioMapper.mapToPortfolio(portfolioDTO));

        return portfolioDTO;

    }
}
