package com.cryptoapp.service;

import com.cryptoapp.dto.TransactionDTO;
import com.cryptoapp.dto.mapper.CryptoCurrencyMapper;
import com.cryptoapp.dto.mapper.TransactionMapper;
import com.cryptoapp.model.CryptoCurrency;
import com.cryptoapp.model.CryptoCurrencyRate;
import com.cryptoapp.model.Currency;
import com.cryptoapp.model.TransactionType;
import com.cryptoapp.repository.CryptoCurrencyRateRepository;
import com.cryptoapp.repository.CryptoCurrencyRepository;
import com.cryptoapp.repository.CurrencyRepo;
import com.cryptoapp.repository.TransactionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final CurrencyService currencyService;
    private final CryptoCurrencyService cryptoCurrencyService;
    private final TransactionRepo transactionRepo;
    
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    
    private final CurrencyRepo currencyRepo;

    private final CryptoCurrencyRateService cryptoCurrencyRateService;

    private final CryptoCurrencyRateRepository cryptoCurrencyRateRepository;

    @Autowired
    public TransactionService(CurrencyService currencyService, CryptoCurrencyService cryptoCurrencyService, TransactionRepo transactionRepo, CryptoCurrencyRepository cryptoCurrencyRepository, CurrencyRepo currencyRepo, CryptoCurrencyRateService cryptoCurrencyRateService, CryptoCurrencyRateRepository cryptoCurrencyRateRepository) {
        this.currencyService = currencyService;
        this.cryptoCurrencyService = cryptoCurrencyService;
        this.transactionRepo = transactionRepo;
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.currencyRepo = currencyRepo;
        this.cryptoCurrencyRateService = cryptoCurrencyRateService;
        this.cryptoCurrencyRateRepository = cryptoCurrencyRateRepository;
    }
    @Transactional
    public TransactionDTO addTransaction(TransactionDTO transactionDTO, Long currencyId, Long cryptoCurrencyId) throws JsonProcessingException {
       CryptoCurrency cryptoCurrency= cryptoCurrencyRepository.findById(cryptoCurrencyId).orElseThrow();
       Currency currency = currencyRepo.findById(currencyId).orElseThrow();
        CryptoCurrencyRate cryptoCurrencyRate = new CryptoCurrencyRate();
        cryptoCurrencyRate.setCryptoCurrency(cryptoCurrency);
        cryptoCurrencyRate.setPrice(cryptoCurrencyRateService.getPrice(cryptoCurrency.getSymbol()));
        cryptoCurrencyRateRepository.save(cryptoCurrencyRate);
        transactionDTO.setPrice(cryptoCurrencyRateService.getPrice(cryptoCurrency.getSymbol()));
        if(transactionDTO.getTransactionType() == TransactionType.sellCrypto){

            cryptoCurrency.setQuantity(cryptoCurrency.getQuantity().subtract(transactionDTO.getAmount()));
            currency.setQuantity(currency.getQuantity().add(transactionDTO.getAmount().multiply(transactionDTO.getPrice())));
            currencyRepo.save(currency);
            cryptoCurrencyRepository.save(cryptoCurrency);
        }
        if(transactionDTO.getTransactionType() == TransactionType.buyCrypto){

            cryptoCurrency.setQuantity(transactionDTO.getAmount());
            currency.setQuantity(currency.getQuantity().subtract(transactionDTO.getAmount().multiply(transactionDTO.getPrice())));
            currencyRepo.save(currency);
            cryptoCurrencyRepository.save(cryptoCurrency);
        }
        transactionRepo.save(TransactionMapper.mapToCurrency(transactionDTO));
            return transactionDTO;
    }

}
