package com.fis_2025_g6.auth;

public class AuthRequest {
    private String username;
    private String password;
    private String email;
    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String val) {
        username = val;
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

    public String getType() {
        return type;
    }

    public void setType(String val) {
        password = val;
    }
}
