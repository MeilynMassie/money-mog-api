package com.mjm.moneymog.service;

import java.util.List;
import java.util.UUID;

import com.mjm.moneymog.entity.User;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(UUID id);
    User getUserByEmail(String email);
    void deleteUser(UUID id);
    User createUser(User user);
}
