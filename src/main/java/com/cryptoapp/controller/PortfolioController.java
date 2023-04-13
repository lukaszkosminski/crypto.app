package com.cryptoapp.controller;

import com.cryptoapp.dto.PortfolioDTO;
import com.cryptoapp.model.User;
import com.cryptoapp.service.PortfolioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {

    private PortfolioService portfolioService;
@Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("api/user/portfolio-total-balance")
    public PortfolioDTO getPortfolioTotalValue(@AuthenticationPrincipal User user) throws JsonProcessingException {
        return portfolioService.calculateTotalAssetBalance(user);
    }


}
