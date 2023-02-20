package com.cryptoapp.repository;

import com.cryptoapp.model.CryptoCurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoCurrencyRateRepository extends JpaRepository<CryptoCurrencyRate, Long> {
}
