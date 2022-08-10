package com.acoustic.zenedit.service;

import java.util.List;
import java.util.Optional;

import com.acoustic.zenedit.entity.User;

public interface UserService {
    List<User> getUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    User validateAndGetUserById(Long id);

    User saveUser(User user);

    void deleteUser(User user);
}
