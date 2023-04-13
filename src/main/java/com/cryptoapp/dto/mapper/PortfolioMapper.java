package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.PortfolioDTO;
import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.model.Portfolio;
import com.cryptoapp.model.User;

public class PortfolioMapper {

    public static Portfolio mapToPortfolio(PortfolioDTO portfolioDTO) {
        Portfolio portfolio = new Portfolio();
        portfolio.setTotalAssetBalance(portfolioDTO.getTotalAssetBalance());
        portfolio.setCurrencyAssetBalance(portfolioDTO.getCurrencyAssetBalance());
        portfolio.setCryptoCurrencyAssetBalance(portfolioDTO.getCryptoCurrencyAssetBalance());
        return portfolio;
    }

    public static PortfolioDTO mapToDTO(Portfolio portfolio) {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setTotalAssetBalance(portfolio.getTotalAssetBalance());
        portfolioDTO.setCurrencyAssetBalance(portfolio.getCurrencyAssetBalance());
        portfolioDTO.setCryptoCurrencyAssetBalance(portfolio.getCryptoCurrencyAssetBalance());
        return portfolioDTO;
    }
}
