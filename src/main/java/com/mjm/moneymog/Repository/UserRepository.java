package com.mjm.moneymog.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mjm.moneymog.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);   

}
