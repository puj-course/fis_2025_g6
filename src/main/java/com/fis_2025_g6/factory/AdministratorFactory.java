package com.fis_2025_g6.factory;

import org.springframework.stereotype.Component;

import com.fis_2025_g6.entity.Administrator;
import com.fis_2025_g6.entity.User;

@Component("administratorFactory")
public class AdministratorFactory extends UserFactory {
    @Override
    public User createUser(String username, String email, String password, String phoneNumber, String address) {
        Administrator administrator = new Administrator();
        administrator.setUsername(username);
        administrator.setEmail(email);
        administrator.setPassword(password);
        administrator.setPhoneNumber(phoneNumber);
        administrator.setAddress(address);
        return administrator;
    }
}
