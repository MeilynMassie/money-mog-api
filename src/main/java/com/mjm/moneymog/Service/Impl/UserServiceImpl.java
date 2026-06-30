package com.mjm.moneymog.Service.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjm.moneymog.Repository.UserRepository;
import com.mjm.moneymog.Service.UserService;
import com.mjm.moneymog.entity.User;
import com.mjm.moneymog.exception.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("User", id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                    new ResourceNotFoundException("User", email));
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User", id));
        userRepository.delete(user);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
