package com.mjm.moneymog.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjm.moneymog.service.TransactionService;
import com.mjm.moneymog.entity.Transaction;
import com.mjm.moneymog.exception.ResourceNotFoundException;
import com.mjm.moneymog.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Transaction not found with id: " + id));
    }

    @Override
    public void deleteTransaction(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Transaction not found with id: " + id));
        transactionRepository.delete(transaction);
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    
}
