package com.cryptoapp.repository;

import com.cryptoapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {
}
