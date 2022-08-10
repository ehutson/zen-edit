package com.acoustic.zenedit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.acoustic.zenedit.entity.User;
import com.acoustic.zenedit.exception.UserNotFoundException;
import com.acoustic.zenedit.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean hasUserWithUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User validateAndGetUserByUsername(final String username) {
        return getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found.", username)));
    }

    @Override
    public User validateAndGetUserById(final Long id) {
        return getUserById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %d not found.", id)));
    }

    @Override
    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        userRepository.delete(user);
    }
}
