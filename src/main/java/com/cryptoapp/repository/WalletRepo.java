package com.cryptoapp.repository;

import com.cryptoapp.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet,Long> {
}
