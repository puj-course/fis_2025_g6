package com.fis_2025_g6.auth;

public class AuthResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public AuthResponse(String token) {
        this.token = token;
    }
}
