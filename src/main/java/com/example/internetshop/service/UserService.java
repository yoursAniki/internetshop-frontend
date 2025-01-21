package com.example.internetshop.service;

import com.example.internetshop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);
}