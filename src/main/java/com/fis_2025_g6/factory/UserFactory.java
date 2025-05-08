package com.fis_2025_g6.factory;

import org.springframework.stereotype.Component;

import com.fis_2025_g6.entity.User;

@Component
public abstract class UserFactory {
    public User create(String name, String email, String password, String phoneNumber, String address) {
        User user = createUser(name, email, password, phoneNumber, address);
        return user;
    }

    protected abstract User createUser(String name, String email, String password, String phoneNumber, String address);
}
