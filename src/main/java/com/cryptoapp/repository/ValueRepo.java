package com.cryptoapp.repository;

import com.cryptoapp.model.Currency;
import com.cryptoapp.model.Value;
import com.cryptoapp.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ValueRepo extends JpaRepository<Value,Long> {
    List<Value> findByWallet_IdWallet(Long idWallet);
//    Value findByWallet_IdWallet(Long idWallet);
    Optional<Value> findByCurrency(Currency currency);

    boolean existsByWalletAndCurrency_Symbol(Long walletId, String symbol);

    void deleteByWallet(Wallet wallet);
}
