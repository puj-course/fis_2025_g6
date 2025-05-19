package com.fis_2025_g6;

import com.fis_2025_g6.auth.CustomUserDetails;
import com.fis_2025_g6.entity.Administrator;
import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    @Test
    void testGetAuthoritiesForAdoptant() {
        Adoptant adoptant = new Adoptant();
        adoptant.setUsername("adoptante1");
        adoptant.setPassword("password");
        CustomUserDetails userDetails = new CustomUserDetails(adoptant);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADOPTANT")));
    }

    @Test
    void testGetAuthoritiesForRefuge() {
        Refuge refuge = new Refuge();
        refuge.setUsername("refugio1");
        refuge.setPassword("password");
        CustomUserDetails userDetails = new CustomUserDetails(refuge);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_REFUGE")));
    }

    @Test
    void testGetAuthoritiesForAdministrator() {
        Administrator admin = new Administrator();
        admin.setUsername("admin1");
        admin.setPassword("password");
        CustomUserDetails userDetails = new CustomUserDetails(admin);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testGetAuthoritiesForGenericUser() {
        User user = new User();
        user.setUsername("usuario");
        user.setPassword("password");
        CustomUserDetails userDetails = new CustomUserDetails(user);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testGetUsernameAndPassword() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("secret");
        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("secret", userDetails.getPassword());
    }

    @Test
    void testAccountStatusFlagsAlwaysTrue() {
        User user = new User();
        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testGetUserReturnsWrappedUser() {
        User user = new User();
        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertSame(user, userDetails.getUser());
    }
}
