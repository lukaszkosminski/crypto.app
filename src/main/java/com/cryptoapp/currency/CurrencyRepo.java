package com.cryptoapp.currency;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepo extends JpaRepository<Currency,Long> {
}
