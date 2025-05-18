package com.fis_2025_g6.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fis_2025_g6.entity.Administrator;
import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.entity.User;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role;
        if (user instanceof Adoptant) {
            role = "ROLE_ADOPTANT";
        } else if (user instanceof Refuge) {
            role = "ROLE_REFUGE";
        } else if (user instanceof Administrator) {
            role = "ROLE_ADMIN";
        } else {
            role = "ROLE_USER";
        }
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }
}
