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
import com.fis_2025_g6.controller.RefugeController;
import com.fis_2025_g6.dto.RefugeDto;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.factory.RefugeFactory;
import com.fis_2025_g6.service.RefugeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RefugeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RefugeService refugeService;

    @Mock
    private RefugeFactory refugeFactory;

    @InjectMocks
    private RefugeController refugeController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(refugeController).build();
    }

    @Test
    void testFindAll() throws Exception {
        Refuge r1 = new Refuge();
        r1.setId(1L);
        r1.setUsername("refuge1");
        r1.setRefugeName("Refugio Uno");

        Refuge r2 = new Refuge();
        r2.setId(2L);
        r2.setUsername("refuge2");
        r2.setRefugeName("Refugio Dos");

        when(refugeService.findAll()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/refugios")
                .with(user("adoptanteUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].username").value("refuge1"))
            .andExpect(jsonPath("$[1].refugeName").value("Refugio Dos"));
    }

    @Test
    void testFindById_Found() throws Exception {
        Refuge refuge = new Refuge();
        refuge.setId(1L);
        refuge.setUsername("refuge1");
        refuge.setRefugeName("Refugio Uno");

        when(refugeService.findById(1L)).thenReturn(Optional.of(refuge));

        mockMvc.perform(get("/refugios/1")
                .with(user("adoptanteUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("refuge1"))
            .andExpect(jsonPath("$.refugeName").value("Refugio Uno"));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(refugeService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/refugios/1")
                .with(user("adoptanteUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testFindPetsByRefuge_Found() throws Exception {
        Refuge refuge = new Refuge();
        refuge.setId(1L);
        Pet pet1 = new Pet();
        pet1.setId(10L);
        Pet pet2 = new Pet();
        pet2.setId(20L);
        refuge.setPets(List.of(pet1, pet2));

        when(refugeService.findById(1L)).thenReturn(Optional.of(refuge));

        mockMvc.perform(get("/refugios/1/mascotas")
                .with(user("adoptanteUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testFindPetsByRefuge_NotFound() throws Exception {
        when(refugeService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/refugios/1/mascotas")
                .with(user("adoptanteUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testFindDonationsByRefuge_Found() throws Exception {
        Refuge refuge = new Refuge();
        refuge.setId(1L);
        Donation d1 = new Donation();
        Donation d2 = new Donation();
        refuge.setReceivedDonations(List.of(d1, d2));

        when(refugeService.findById(1L)).thenReturn(Optional.of(refuge));

        mockMvc.perform(get("/refugios/1/donaciones")
                .with(user("refugeUser").roles("REFUGE"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testFindDonationsByRefuge_NotFound() throws Exception {
        when(refugeService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/refugios/1/donaciones")
                .with(user("refugeUser").roles("REFUGE"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        RefugeDto dto = new RefugeDto();
        dto.setUsername("newrefuge");
        dto.setEmail("newrefuge@example.com");
        dto.setPassword("password123");
        dto.setPhoneNumber("+123456789");
        dto.setAddress("Address 1");
        dto.setRefugeName("Nuevo Refugio");
        dto.setDescription("Descripción del refugio");

        Refuge created = new Refuge();
        created.setId(100L);
        created.setUsername(dto.getUsername());
        created.setEmail(dto.getEmail());
        created.setPassword(dto.getPassword());
        created.setPhoneNumber(dto.getPhoneNumber());
        created.setAddress(dto.getAddress());
        created.setRefugeName(dto.getRefugeName());
        created.setDescription(dto.getDescription());

        when(refugeFactory.create(
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getPhoneNumber(),
                dto.getAddress())).thenReturn(created);

        when(refugeService.create(any(Refuge.class))).thenReturn(created);

        mockMvc.perform(post("/refugios")
                .with(user("adminUser").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/refugios/100"))
            .andExpect(jsonPath("$.username").value("newrefuge"))
            .andExpect(jsonPath("$.refugeName").value("Nuevo Refugio"));
    }

    @Test
    void testDelete_Found() throws Exception {
        when(refugeService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/refugios/1")
                .with(user("adminUser").roles("ADMIN")))
            .andExpect(status().isNoContent());

        verify(refugeService).delete(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(refugeService.delete(1L)).thenReturn(false);

        mockMvc.perform(delete("/refugios/1")
                .with(user("adminUser").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
