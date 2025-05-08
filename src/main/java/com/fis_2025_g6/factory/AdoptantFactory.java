package com.fis_2025_g6.factory;

import org.springframework.stereotype.Component;

import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.entity.User;

@Component("adoptantFactory")
public class AdoptantFactory extends UserFactory {
    @Override
    public User createUser(String name, String email, String password, String phoneNumber, String address) {
        Adoptant adoptant = new Adoptant();
        adoptant.setName(name);
        adoptant.setEmail(email);
        adoptant.setPassword(password);
        adoptant.setPhoneNumber(phoneNumber);
        adoptant.setAddress(address);
        adoptant.setAdoptantName(name);
        return adoptant;
    }
}
