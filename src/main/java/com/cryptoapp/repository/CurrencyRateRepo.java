package com.cryptoapp.repository;

import com.cryptoapp.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRateRepo extends JpaRepository<CurrencyRate,Long> {


}
