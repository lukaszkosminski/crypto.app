package com.cryptoapp.service;

import com.cryptoapp.dto.TransactionDTO;
import com.cryptoapp.dto.mapper.TransactionMapper;
import com.cryptoapp.model.*;
import com.cryptoapp.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class TransactionService {

    private final CurrencyService currencyService;

    private final TransactionRepo transactionRepo;

    private final UserRepo userRepo;

    private final CurrencyRepo currencyRepo;

//    private final CryptoCurrencyRateService cryptoCurrencyRateService;

    private final ValueService valueService;

    private final ValueRepo valueRepo;
    private final WalletRepo walletRepo;
    private final CryptoSymbolChecker cryptoSymbolChecker;
    private final  CurrencyRateService currencyRateService;

    @Autowired
    public TransactionService(CurrencyService currencyService, TransactionRepo transactionRepo, UserRepo userRepo, CurrencyRepo currencyRepo,  ValueService valueService, ValueRepo valueRepo, WalletRepo walletRepo, CryptoSymbolChecker cryptoSymbolChecker, CurrencyRateService currencyRateService) {
        this.currencyService = currencyService;
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
        this.currencyRepo = currencyRepo;

        this.valueService = valueService;
        this.valueRepo = valueRepo;
        this.walletRepo = walletRepo;
        this.cryptoSymbolChecker = cryptoSymbolChecker;
        this.currencyRateService = currencyRateService;
    }

    public TransactionDTO addTransactionDepositFiatCurrency(TransactionDTO transactionDTO, User userAuth, Long walletId) throws JsonProcessingException {
        System.out.println(transactionDTO.getQuoteCurrencySymbol());
        User user = userRepo.findByLogin(userAuth.getLogin());
        Wallet wallet = user.getWalletList().stream()
                .filter(w -> w.getIdWallet().equals(walletId))
                .findFirst()
                .orElse(null);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet with id " + walletId + " not found for user with  " + userAuth.getLogin());
        }
        Transaction transaction = TransactionMapper.mapToEntity(transactionDTO);
        transaction.setTransactionType(TransactionType.DEPOSIT_FIAT_CURRENCY);

            if(valueService.checkWallet(walletId,transactionDTO.getBaseCurrencySymbol())){
                Currency currency = currencyRepo.findBySymbol(transactionDTO.getBaseCurrencySymbol()).orElseThrow();
                Value value = valueRepo.findByCurrency(currency).orElseThrow();
                value.setQuantity(value.getQuantity().add(transactionDTO.getBaseCurrencyAmount()));
                System.out.println(value);
                valueRepo.save(value);
//                transaction.setValue(value);
                transaction.setWallet(walletRepo.findById(walletId).orElseThrow());
            } else {
                Currency currency = currencyRepo.findBySymbol(transactionDTO.getBaseCurrencySymbol()).orElseGet(() -> {
                    Currency newCurrency = new Currency();
                    newCurrency.setSymbol(transactionDTO.getBaseCurrencySymbol());
                    return currencyRepo.save(newCurrency);
                });
                currency.setSymbol(transactionDTO.getBaseCurrencySymbol());
                currency.setIsCrypto(false);
                currencyRepo.save(currency);
                Value value = new Value();
                value.setWallet(wallet);
                value.setQuantity(transaction.getBaseCurrencyAmount());
                value.setCurrency(currency);
                valueRepo.save(value);
//                transaction.setValue(value);
                transaction.setWallet(wallet);
            }


        transactionRepo.save(transaction);
        return transactionDTO;

    }
        public TransactionDTO addTransactionCurrencyCurrency(TransactionDTO transactionDTO, User userAuth, Long walletId) throws JsonProcessingException {
        System.out.println(transactionDTO.getQuoteCurrencySymbol());
        User user = userRepo.findByLogin(userAuth.getLogin());
        Wallet wallet = user.getWalletList().stream()
                .filter(w -> w.getIdWallet().equals(walletId))
                .findFirst()
                .orElse(null);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet with id " + walletId + " not found for user with  " + userAuth.getLogin());
        }
        Transaction transaction = TransactionMapper.mapToEntity(transactionDTO);
        transaction.setTransactionType(TransactionType.CURRENCY_CURRENCY);

//            Currency baseQurrency = currencyRepo.findBySymbol(transaction.getBaseCurrencySymbol()).orElseThrow();
            Currency baseCurrency = currencyRepo.findBySymbol(transaction.getBaseCurrencySymbol())
                    .orElseGet(() -> {
                        Currency newCurrency = new Currency();
                        newCurrency.setSymbol(transaction.getBaseCurrencySymbol());
                        newCurrency.setIsCrypto(cryptoSymbolChecker.checkIsCrypto(transaction.getBaseCurrencySymbol()));
                        return currencyRepo.save(newCurrency);
                    });
            Currency quoteCurrency = currencyRepo.findBySymbol(transaction.getQuoteCurrencySymbol())
                    .orElseThrow(() -> new NoSuchElementException("Object Quote Currency not found"+ transaction.getQuoteCurrencySymbol()));
//            Value baseValue = valueRepo.findByCurrency(baseCurrency).orElseThrow();
            Value baseValue = valueRepo.findByCurrency(baseCurrency)
                    .orElseGet(() -> {
                        Value newValue = new Value();
                        newValue.setQuantity(BigDecimal.valueOf(0));
                        newValue.setWallet(wallet);
                        newValue.setCurrency(baseCurrency);

                        return valueRepo.save(newValue);
                    });

            Value quoteValue = valueRepo.findByCurrency(quoteCurrency).orElseThrow();
            BigDecimal price = currencyRateService.getPrice(baseValue.getCurrency().getSymbol(), quoteValue.getCurrency().getSymbol());
            baseValue.setQuantity(baseValue.getQuantity().add(transaction.getBaseCurrencyAmount()));
            quoteValue.setQuantity(quoteValue.getQuantity().subtract(baseValue.getQuantity().multiply(price)));
            transaction.setPriceUsd(price);
            transaction.setQuoteCurrencyAmount(price.multiply(baseValue.getQuantity()));
            transaction.setWallet(wallet);


        transactionRepo.save(transaction);
        return transactionDTO;


}


    public TransactionDTO addTransactionWithdrawFiatCurrency(TransactionDTO transactionDTO, User userAuth, Long walletId) throws JsonProcessingException {
        System.out.println(transactionDTO.getQuoteCurrencySymbol());
        User user = userRepo.findByLogin(userAuth.getLogin());
        Wallet wallet = user.getWalletList().stream()
                .filter(w -> w.getIdWallet().equals(walletId))
                .findFirst()
                .orElse(null);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet with id " + walletId + " not found for user with  " + userAuth.getLogin());
        }
        Transaction transaction = TransactionMapper.mapToEntity(transactionDTO);


        transaction.setTransactionType(TransactionType.WITHDRAW_FIAT_CURRENCY);
            Currency currency = currencyRepo.findBySymbol(transactionDTO.getQuoteCurrencySymbol()).orElseThrow(() -> new RuntimeException("Currency not found"));
            Value value = valueRepo.findByCurrency(currency).orElseThrow(() -> new RuntimeException("Value not found"));
            value.setQuantity(value.getQuantity().subtract(transaction.getQuoteCurrencyAmount()));
            transaction.setWallet(wallet);
            valueRepo.save(value);

        transactionRepo.save(transaction);
        return transactionDTO;

    }
}





