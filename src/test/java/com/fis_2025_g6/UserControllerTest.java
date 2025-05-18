package com.fis_2025_g6;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis_2025_g6.controller.UserController;
import com.fis_2025_g6.dto.UserDto;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.factory.UserFactory;
import com.fis_2025_g6.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserFactory userFactory;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testFindAll() throws Exception {
        User a1 = new User();
        a1.setId(1L);
        a1.setUsername("adopt1");

        User a2 = new User();
        a2.setId(2L);
        a2.setUsername("adopt2");

        when(userService.findAll()).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/usuarios")
                .with(user("adminUser").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].username").value("adopt1"));
    }

    @Test
    void testFindById_Found() throws Exception {
        User adoptant = new User();
        adoptant.setId(1L);
        adoptant.setUsername("adopt1");

        when(userService.findById(1L)).thenReturn(Optional.of(adoptant));

        mockMvc.perform(get("/usuarios/1")
                .with(user("adminUser").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("adopt1"));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuarios/1")
                .with(user("adminUser").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        UserDto dto = new UserDto();
        dto.setUsername("newadopt");
        dto.setEmail("newadopt@example.com");
        dto.setPassword("pass123333");
        dto.setPhoneNumber("+123456789");
        dto.setAddress("Address 1");

        User created = new User();
        created.setId(100L);
        created.setUsername(dto.getUsername());
        created.setEmail(dto.getEmail());
        created.setPassword(dto.getPassword());
        created.setPhoneNumber(dto.getPhoneNumber());
        created.setAddress(dto.getAddress());

        when(userFactory.create(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPhoneNumber(),
                dto.getAddress())).thenReturn(created);

        when(userService.create(any(User.class))).thenReturn(created);

        mockMvc.perform(post("/usuarios")
            .with(user("adminUser").roles("ADMIN"))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated());
    }

    @Test
    void testDelete_Found() throws Exception {
        when(userService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/usuarios/1")
                .with(user("adminUser").roles("ADMIN")))
            .andExpect(status().isNoContent());

        verify(userService).delete(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(userService.delete(1L)).thenReturn(false);

        mockMvc.perform(delete("/usuarios/1")
                .with(user("adminUser").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
