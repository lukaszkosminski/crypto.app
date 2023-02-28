package com.cryptoapp.controller;

import com.cryptoapp.dto.PortfolioDTO;
import com.cryptoapp.service.PortfolioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {

    private PortfolioService portfolioService;
@Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("portfolio/{idUser}")
    public PortfolioDTO getPortfolioValue(Long idUser) throws JsonProcessingException {
        return portfolioService.calculateTotalValue(Long.valueOf(1));
    }


}
