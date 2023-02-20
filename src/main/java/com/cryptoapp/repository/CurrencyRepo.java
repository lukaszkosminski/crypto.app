package com.cryptoapp.repository;

import com.cryptoapp.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepo extends JpaRepository<Currency,Long> {
}
