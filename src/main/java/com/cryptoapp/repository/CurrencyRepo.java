package com.cryptoapp.repository;

import com.cryptoapp.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency,Long> {
    Optional<Currency> findBySymbol(String symbol);

    @Query("SELECT COUNT(c) > 0 FROM Currency c WHERE c.symbol = :symbol")
    boolean existsBySymbol(@Param("symbol") String symbol);
}