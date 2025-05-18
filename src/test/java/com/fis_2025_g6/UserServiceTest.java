package com.fis_2025_g6;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.repository.UserRepository;
import com.fis_2025_g6.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setEmail("user1@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUsername());
    }

    @Test
    void testFindAll() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.findAll();

        assertEquals(2, users.size());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
    }

    @Test
    void testCreate() {
        User user = new User();
        user.setUsername("newuser");

        when(userRepository.save(user)).thenReturn(user);

        User created = userService.create(user);

        assertNotNull(created);
        assertEquals("newuser", created.getUsername());
    }

    @Test
    void testDelete_Exists() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);

        boolean deleted = userService.delete(id);

        assertTrue(deleted);
        verify(userRepository).deleteById(id);
    }

    @Test
    void testDelete_NotExists() {
        Long id = 99L;
        when(userRepository.existsById(id)).thenReturn(false);

        boolean deleted = userService.delete(id);

        assertFalse(deleted);
        verify(userRepository, never()).deleteById(id);
    }
}
