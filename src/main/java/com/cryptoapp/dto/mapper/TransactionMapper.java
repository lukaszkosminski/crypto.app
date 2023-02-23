package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.CryptoCurrencyDTO;
import com.cryptoapp.dto.TransactionDTO;
import com.cryptoapp.model.CryptoCurrency;
import com.cryptoapp.model.Transaction;

public class TransactionMapper {

    public static Transaction mapToCurrency(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setPrice(transactionDTO.getPrice());
        return transaction;
    }

    public static TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setPrice(transaction.getPrice());
        return transactionDTO;
    }
}
