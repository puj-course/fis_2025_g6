package com.fis_2025_g6.factory;

import org.springframework.stereotype.Component;

import com.fis_2025_g6.entity.User;

@Component
public abstract class UserFactory {
    public User create(String username, String email, String password, String phoneNumber, String address) {
        return createUser(username, email, password, phoneNumber, address);
    }

    protected abstract User createUser(String username, String email, String password, String phoneNumber, String address);
}
