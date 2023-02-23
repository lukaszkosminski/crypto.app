package com.cryptoapp.repository;

import com.cryptoapp.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {
    @Query("SELECT COUNT(c) > 0 FROM CryptoCurrency c WHERE c.symbol = :symbol")
    boolean existsBySymbol(@Param("symbol") String symbol);
}
