package com.cryptoapp.repositories;

import com.cryptoapp.entities.WalletFiatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletFiatRepository extends JpaRepository<WalletFiatEntity,Long> {
}
