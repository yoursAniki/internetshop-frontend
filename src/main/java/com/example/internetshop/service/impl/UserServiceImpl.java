package com.example.internetshop.service.impl;

import com.example.internetshop.model.User;
import com.example.internetshop.repository.UserRepository;
import com.example.internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    user.setPasswordHash(updatedUser.getPasswordHash()); // В реальном приложении нужно обрабатывать пароли безопасно
                    return userRepository.save(user);
                })
                .orElse(null); // Или выбросить исключение
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}