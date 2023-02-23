package com.cryptoapp.controller;

import com.cryptoapp.dto.TransactionDTO;
import com.cryptoapp.model.CryptoCurrency;
import com.cryptoapp.repository.CryptoCurrencyRepository;
import com.cryptoapp.repository.CurrencyRepo;
import com.cryptoapp.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CurrencyRepo currencyRepo;

    @Autowired
    public TransactionController(TransactionService transactionService, CryptoCurrencyRepository cryptoCurrencyRepository, CurrencyRepo currencyRepo) {
        this.transactionService = transactionService;
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.currencyRepo = currencyRepo;
    }
    @PostMapping("transaction/add/currencyId/{currencyId}/cryptoCurrency/{cryptoCurrencyId}")
    public TransactionDTO addTransaction(@RequestBody TransactionDTO transactionDTO, @PathVariable Long currencyId, @PathVariable Long cryptoCurrencyId) throws JsonProcessingException {
        return transactionService.addTransaction(transactionDTO,currencyId,cryptoCurrencyId);
    }
}
