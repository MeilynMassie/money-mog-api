package com.mjm.moneymog.Service;

import java.util.List;
import java.util.UUID;

import com.mjm.moneymog.entity.Account;

public interface AccountService {
    List<Account> getAllAccounts();
    Account getAccountById(UUID id);
    void deleteAccount(UUID id);
    Account createAccount(Account account);
}
