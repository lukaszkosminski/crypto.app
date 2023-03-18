package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.TransactionDTO;
import com.cryptoapp.model.Transaction;

public class TransactionMapper {

    public static Transaction mapToEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setBaseCurrencyAmount(transactionDTO.getBaseCurrencyAmount());
        transaction.setQuoteCurrencyAmount(transactionDTO.getQuoteCurrencyAmount());
        transaction.setPriceUsd(transactionDTO.getPriceUsd());
        transaction.setBaseCurrencySymbol(transactionDTO.getBaseCurrencySymbol());
        transaction.setQuoteCurrencySymbol(transactionDTO.getQuoteCurrencySymbol());

//        transaction.setQuoteCurrency(transactionDTO.getQuoteCurrency());
//        transaction.setValue(transactionDTO.getValue());
//        transaction.setWallet(transactionDTO.getWallet());

        return transaction;
    }

    public static TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setBaseCurrencyAmount(transaction.getBaseCurrencyAmount());
        transactionDTO.setQuoteCurrencyAmount(transaction.getQuoteCurrencyAmount());
//        transactionDTO.setValue(transaction.getValue());
//        transactionDTO.setQuoteCurrency(transaction.getQuoteCurrency());
        transactionDTO.setBaseCurrencySymbol(transaction.getBaseCurrencySymbol());
        transactionDTO.setQuoteCurrencySymbol(transaction.getQuoteCurrencySymbol());
//        transactionDTO.setWallet(transaction.getWallet());
        transactionDTO.setPriceUsd(transaction.getPriceUsd());
        return transactionDTO;
    }
}
