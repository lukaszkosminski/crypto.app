package com.cryptoapp.controller;


import com.cryptoapp.dto.TransactionDTO;
import com.cryptoapp.model.User;
import com.cryptoapp.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("api/user/wallet/{walletId}/deposit")
    @ResponseBody
    public TransactionDTO addTransactionDepositFiatCurrency(@RequestBody TransactionDTO transactionDTO, @AuthenticationPrincipal User user, @PathVariable Long walletId) throws JsonProcessingException {
        return transactionService.addTransactionDepositFiatCurrency(transactionDTO,user,walletId);
    }
    @PostMapping("api/user/wallet/{walletId}/currency-currency")
    @ResponseBody
    public TransactionDTO addTransactionCurrencyCurrency(@RequestBody TransactionDTO transactionDTO, @AuthenticationPrincipal User user, @PathVariable Long walletId) throws JsonProcessingException {
        return transactionService.addTransactionCurrencyCurrency(transactionDTO,user,walletId);
    }
    @PostMapping("api/user/wallet/{walletId}/withdraw")
    @ResponseBody
    public TransactionDTO addTransactionWithdrawFiatCurrency(@RequestBody TransactionDTO transactionDTO, @AuthenticationPrincipal User user, @PathVariable Long walletId) throws JsonProcessingException {
        return transactionService.addTransactionWithdrawFiatCurrency(transactionDTO,user,walletId);
    }

}