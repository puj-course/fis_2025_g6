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
import com.fis_2025_g6.controller.AdoptantController;
import com.fis_2025_g6.dto.AdoptantDto;
import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.factory.AdoptantFactory;
import com.fis_2025_g6.service.AdoptantService;
import com.fis_2025_g6.service.ApplicationService;
import com.fis_2025_g6.service.DonationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class AdoptantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdoptantService adoptantService;

    @Mock
    private ApplicationService applicationService;

    @Mock
    private DonationService donationService;

    @Mock
    private AdoptantFactory adoptantFactory;

    @InjectMocks
    private AdoptantController adoptantController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adoptantController).build();
    }

    @Test
    void testFindAll() throws Exception {
        Adoptant a1 = new Adoptant();
        a1.setId(1L);
        a1.setUsername("adopt1");
        a1.setAdoptantName("Adoptante Uno");

        Adoptant a2 = new Adoptant();
        a2.setId(2L);
        a2.setUsername("adopt2");
        a2.setAdoptantName("Adoptante Dos");

        when(adoptantService.findAll()).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/adoptantes")
                .with(user("refugeUser").roles("REFUGE"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].username").value("adopt1"))
            .andExpect(jsonPath("$[1].adoptantName").value("Adoptante Dos"));
    }

    @Test
    void testFindById_Found() throws Exception {
        Adoptant adoptant = new Adoptant();
        adoptant.setId(1L);
        adoptant.setUsername("adopt1");
        adoptant.setAdoptantName("Adoptante Uno");

        when(adoptantService.findById(1L)).thenReturn(Optional.of(adoptant));

        mockMvc.perform(get("/adoptantes/1")
                .with(user("adminUser").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("adopt1"))
            .andExpect(jsonPath("$.adoptantName").value("Adoptante Uno"));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(adoptantService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/adoptantes/1")
                .with(user("adminUser").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetApplications_Found() throws Exception {
        Adoptant adoptant = new Adoptant();
        adoptant.setId(1L);
        when(adoptantService.findById(1L)).thenReturn(Optional.of(adoptant));

        Application app1 = new Application();
        Application app2 = new Application();
        when(applicationService.findByAdoptantId(1L)).thenReturn(List.of(app1, app2));

        mockMvc.perform(get("/adoptantes/1/solicitudes")
                .with(user("adoptUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetApplications_NotFound() throws Exception {
        when(adoptantService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/adoptantes/1/solicitudes")
                .with(user("adoptUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetDonations_Found() throws Exception {
        Adoptant adoptant = new Adoptant();
        adoptant.setId(1L);
        when(adoptantService.findById(1L)).thenReturn(Optional.of(adoptant));

        Donation d1 = new Donation();
        Donation d2 = new Donation();
        when(donationService.findByAdoptantId(1L)).thenReturn(List.of(d1, d2));

        mockMvc.perform(get("/adoptantes/1/donaciones")
                .with(user("adoptUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetDonations_NotFound() throws Exception {
        when(adoptantService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/adoptantes/1/donaciones")
                .with(user("adoptUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        AdoptantDto dto = new AdoptantDto();
        dto.setUsername("newadopt");
        dto.setEmail("newadopt@example.com");
        dto.setPassword("pass123333");
        dto.setPhoneNumber("+123456789");
        dto.setAddress("Address 1");
        dto.setAdoptantName("Nuevo Adoptante");

        Adoptant created = new Adoptant();
        created.setId(100L);
        created.setUsername(dto.getUsername());
        created.setEmail(dto.getEmail());
        created.setPassword(dto.getPassword());
        created.setPhoneNumber(dto.getPhoneNumber());
        created.setAddress(dto.getAddress());
        created.setAdoptantName(dto.getAdoptantName());

        when(adoptantFactory.create(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPhoneNumber(),
                dto.getAddress())).thenReturn(created);

        when(adoptantService.create(any(Adoptant.class))).thenReturn(created);

        mockMvc.perform(post("/adoptantes")
            .with(user("adminUser").roles("ADMIN"))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated());
    }

    @Test
    void testDelete_Found() throws Exception {
        when(adoptantService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/adoptantes/1")
                .with(user("adminUser").roles("ADMIN")))
            .andExpect(status().isNoContent());

        verify(adoptantService).delete(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(adoptantService.delete(1L)).thenReturn(false);

        mockMvc.perform(delete("/adoptantes/1")
                .with(user("adminUser").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
