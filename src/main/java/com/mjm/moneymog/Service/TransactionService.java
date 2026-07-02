package com.mjm.moneymog.service;

import java.util.List;
import java.util.UUID;

import com.mjm.moneymog.entity.Transaction;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(UUID id);
    void deleteTransaction(UUID id);
    Transaction createTransaction(Transaction transaction);
}
