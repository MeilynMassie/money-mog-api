package com.mjm.moneymog.Repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mjm.moneymog.entity.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    
}
