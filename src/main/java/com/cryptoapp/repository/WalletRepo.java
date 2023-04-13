package com.cryptoapp.repository;

import com.cryptoapp.model.User;
import com.cryptoapp.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepo extends JpaRepository<Wallet,Long> {


    void deleteByUser(User user);

    List<Wallet> findAllByUser(User user);
}
