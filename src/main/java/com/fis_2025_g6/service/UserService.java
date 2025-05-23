package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public boolean delete(Long id) {
        boolean existe = userRepository.existsById(id);
        if (existe) {
            userRepository.deleteById(id);
        }
        return existe;
    }
}
