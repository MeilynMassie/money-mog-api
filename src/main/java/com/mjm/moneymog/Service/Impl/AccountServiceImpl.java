package com.mjm.moneymog.Service.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjm.moneymog.Repository.AccountRepository;
import com.mjm.moneymog.Service.AccountService;
import com.mjm.moneymog.entity.Account;
import com.mjm.moneymog.exception.ResourceNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(UUID id) {
        return accountRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Account", id));
    }

    @Override
    public void deleteAccount(UUID id) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Account", id));
        accountRepository.delete(account);
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
    
}
