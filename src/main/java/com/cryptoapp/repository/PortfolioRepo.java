package com.cryptoapp.repository;

import com.cryptoapp.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepo extends JpaRepository<Portfolio,Long> {
}
