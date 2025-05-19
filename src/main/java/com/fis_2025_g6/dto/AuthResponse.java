package com.fis_2025_g6.dto;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class AuthResponse {
    private String token;
    private String userType; // "ADOPTANT", "REFUGE", "ADMIN"

    public AuthResponse(String token, UserDetails userDetails) {
        this.token = token;
        this.userType = userDetails.getAuthorities().iterator().next().getAuthority().substring(5);
    }
}
