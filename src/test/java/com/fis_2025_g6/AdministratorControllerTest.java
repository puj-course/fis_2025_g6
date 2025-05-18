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
import com.fis_2025_g6.controller.AdministratorController;
import com.fis_2025_g6.dto.AdministratorDto;
import com.fis_2025_g6.entity.Administrator;
import com.fis_2025_g6.factory.AdministratorFactory;
import com.fis_2025_g6.service.AdministratorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class AdministratorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdministratorService administratorService;

    @Mock
    private AdministratorFactory administratorFactory;

    @InjectMocks
    private AdministratorController administratorController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(administratorController).build();
    }

    @Test
    void testFindAll() throws Exception {
        Administrator admin1 = new Administrator();
        admin1.setId(1L);
        admin1.setUsername("admin1");
        admin1.setEmail("admin1@example.com");
        admin1.setPassword("password123");
        admin1.setPhoneNumber("+123456789");
        admin1.setAddress("Calle A");

        Administrator admin2 = new Administrator();
        admin2.setId(2L);
        admin2.setUsername("admin2");
        admin2.setEmail("admin2@example.com");
        admin2.setPassword("password456");
        admin2.setPhoneNumber("+987654321");
        admin2.setAddress("Calle B");

        when(administratorService.findAll()).thenReturn(List.of(admin1, admin2));
    
        mockMvc.perform(get("/admins")
                .with(user("admin").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].username").value("admin1"));
    }
@Test
void testFindById_Found() throws Exception {
    Administrator admin = new Administrator();
    admin.setId(1L);
    admin.setUsername("admin1");
    admin.setEmail("admin1@example.com");
    admin.setPassword("password123");
    admin.setPhoneNumber("+123456789");
    admin.setAddress("Calle A");

    when(administratorService.findById(1L)).thenReturn(Optional.of(admin));

    mockMvc.perform(get("/admins/1")
            .accept(MediaType.APPLICATION_JSON)
            .with(user("admin").roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.username").value("admin1"));
    }

    @Test
    void testCreate() throws Exception {
        AdministratorDto dto = new AdministratorDto();
        dto.setUsername("newadmin");
        dto.setEmail("newadmin@example.com");
        dto.setPassword("securePass123");
        dto.setPhoneNumber("+123456789");
        dto.setAddress("Address 123");

        Administrator createdAdmin = new Administrator();
        createdAdmin.setId(10L);
        createdAdmin.setUsername(dto.getUsername());
        createdAdmin.setEmail(dto.getEmail());
        createdAdmin.setPassword(dto.getPassword());
        createdAdmin.setPhoneNumber(dto.getPhoneNumber());
        createdAdmin.setAddress(dto.getAddress());

        when(administratorFactory.create(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPhoneNumber(),
                dto.getAddress())).thenReturn(createdAdmin);

        when(administratorService.create(any(Administrator.class))).thenReturn(createdAdmin);

        mockMvc.perform(post("/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/admins/10"))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.username").value("newadmin"));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(administratorService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/admins/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_Found() throws Exception {
        when(administratorService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/admins/1"))
            .andExpect(status().isNoContent());

        verify(administratorService).delete(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(administratorService.delete(1L)).thenReturn(false);

        mockMvc.perform(delete("/admins/1"))
            .andExpect(status().isNotFound());
    }
}
