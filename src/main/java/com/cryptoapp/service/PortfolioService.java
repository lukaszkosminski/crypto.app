package com.cryptoapp.service;

import com.cryptoapp.dto.PortfolioDTO;
import com.cryptoapp.model.User;
import com.cryptoapp.model.Wallet;
import com.cryptoapp.repository.PortfolioRepo;
import com.cryptoapp.repository.UserRepo;
import com.cryptoapp.repository.ValueRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PortfolioService {

    private final UserService userService;


    private final CurrencyRateService currencyRateService;

    private final UserRepo userRepo;
    private final PortfolioRepo portfolioRepo;

    private final ValueRepo valueRepo;

    private final ValueService valueService;

    @Value("${api.url2}}")
    private String apiUrl;
    @Autowired
    public PortfolioService(UserService userService, CurrencyRateService currencyRateService, UserRepo userRepo, PortfolioRepo portfolioRepo, ValueRepo valueRepo, ValueService valueService) {
        this.userService = userService;
        this.currencyRateService = currencyRateService;
        this.userRepo = userRepo;
        this.portfolioRepo = portfolioRepo;
        this.valueRepo = valueRepo;
        this.valueService = valueService;
    }




    public PortfolioDTO calculateTotalAssetBalance(User userAuth) throws JsonProcessingException {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setTotalAssetBalance(BigDecimal.valueOf(0));
        portfolioDTO.setCryptoCurrencyAssetBalance(BigDecimal.valueOf(0));
        portfolioDTO.setCurrencyAssetBalance(BigDecimal.valueOf(0));
        User user = userRepo.findById(userAuth.getId()).orElseThrow();
        List<Wallet> wallet = user.getWalletList();

        for (Wallet currentWallet : wallet) {

            List<com.cryptoapp.model.Value> byWalletIdWallet = valueRepo.findByWallet_IdWallet(currentWallet.getIdWallet());
            for(com.cryptoapp.model.Value value:byWalletIdWallet){
//                portfolioDTO.setTotalAssetBalance(value.getQuantity().multiply(getPrice(value.getCurrency().getSymbol(),userAuth.getPreferredCurrency())));
                if(value.getCurrency().getIsCrypto().equals(true)){
                    portfolioDTO.setCryptoCurrencyAssetBalance(value.getQuantity().multiply(getPrice(value.getCurrency().getSymbol(),userAuth.getPreferredCurrency())));
                }
                else {
                    portfolioDTO.setCurrencyAssetBalance(value.getQuantity().multiply(getPrice(value.getCurrency().getSymbol(),userAuth.getPreferredCurrency())));

                }
            }


        }
        portfolioDTO.setTotalAssetBalance(portfolioDTO.getCurrencyAssetBalance().add(portfolioDTO.getCryptoCurrencyAssetBalance()));
//
//            List<CryptoCurrency> cryptoCurrency = currentWallet.getCryptoCurrency();
//            for(CryptoCurrency currentCryptoCurrency:cryptoCurrency){
////                BigDecimal price = cryptoCurrencyRateService.getPrice(currentCryptoCurrency.getSymbol());
//                BigDecimal price = calculateAssetValueInPreferredCurrency(currentCryptoCurrency.getSymbol(), user.getPreferredCurrency());
//                BigDecimal cryptoBalance = price.multiply(currentCryptoCurrency.getQuantity());
//                portfolioDTO.setCryptoCurrencyAssetBalance(portfolioDTO.getCryptoCurrencyAssetBalance().add(cryptoBalance));
//                portfolioDTO.setTotalAssetBalance(portfolioDTO.getTotalAssetBalance().add(cryptoBalance));
//            }
//            List<Currency> currency = currentWallet.getCurrency();
//            for(Currency currentCurrency:currency){
////                BigDecimal price = currencyRateService.getPrice(currentCurrency.getSymbol());
//                BigDecimal price = calculateAssetValueInPreferredCurrency(currentCurrency.getSymbol(), user.getPreferredCurrency());
//                BigDecimal currencyBalance = price.multiply(currentCurrency.getQuantity());
//                portfolioDTO.setCurrencyAssetBalance(portfolioDTO.getCurrencyAssetBalance().add(currencyBalance));
//                portfolioDTO.setTotalAssetBalance(portfolioDTO.getTotalAssetBalance().add(currencyBalance));
//            }
//
//
//        }
//        portfolioRepo.save(PortfolioMapper.mapToPortfolio(portfolioDTO));
//
        return portfolioDTO;

    }
    public BigDecimal getPrice(String symbol, String preferredCurrency) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(String.format(apiUrl, symbol,preferredCurrency), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        BigDecimal value = rootNode.get(preferredCurrency).decimalValue();
        return value;
    }

}
