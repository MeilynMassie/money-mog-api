package com.mjm.moneymog.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mjm.moneymog.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    
}
