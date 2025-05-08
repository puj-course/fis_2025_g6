package com.fis_2025_g6.auth;

public class RegisterRequest {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String val) {
        name = val;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String val) {
        password = val;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String val) {
        email = val;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String val) {
        phoneNumber = val;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String val) {
        address = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String val) {
        type = val;
    }
}
