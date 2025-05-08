package com.fis_2025_g6.factory;

import org.springframework.stereotype.Component;

import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.entity.User;

@Component("refugeFactory")
public class RefugeFactory extends UserFactory {
    @Override
    public User createUser(String name, String email, String password, String phoneNumber, String address) {
        Refuge refuge = new Refuge();
        refuge.setName(name);
        refuge.setEmail(email);
        refuge.setPassword(password);
        refuge.setPhoneNumber(phoneNumber);
        refuge.setAddress(address);
        refuge.setRefugeName(name);
        refuge.setDescription("Sin descripción.");
        return refuge;
    }
}
