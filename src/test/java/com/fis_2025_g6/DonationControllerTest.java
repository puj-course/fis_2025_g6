package com.fis_2025_g6;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis_2025_g6.controller.DonationController;
import com.fis_2025_g6.dto.DonationDto;
import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.service.DonationService;
import com.fis_2025_g6.service.RefugeService;
import com.fis_2025_g6.auth.CustomUserDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

public class DonationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DonationService donationService;

    @Mock
    private RefugeService refugeService;

    @InjectMocks
    private DonationController donationController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(donationController).build();
    }

    @Test
    void testFindAll() throws Exception {
        Donation d1 = new Donation();
        Donation d2 = new Donation();
        when(donationService.findAll()).thenReturn(List.of(d1, d2));

        mockMvc.perform(get("/donaciones")
                .with(user("adminUser").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testFindById_Found() throws Exception {
        Donation donation = new Donation();
        donation.setId(1L);
        donation.setAmount(50.0);

        when(donationService.findById(1L)).thenReturn(Optional.of(donation));

        mockMvc.perform(get("/donaciones/1")
                .with(user("adminUser").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.amount").value(50.0));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(donationService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/donaciones/1")
                .with(user("adminUser").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreate_AsNonAdoptant() throws Exception {
        DonationDto dto = new DonationDto();
        dto.setAmount(100.0);
        dto.setRefugeUsername("refugio1");

        User nonAdoptant = new Refuge(); // cualquier otro tipo que no sea Adoptant

        CustomUserDetails principal = mock(CustomUserDetails.class);
        when(principal.getUser()).thenReturn(nonAdoptant);

        mockMvc.perform(post("/donaciones")
                .with(user("user1").roles("REFUGE"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());
    }

    @Test
    void testDelete_Found() throws Exception {
        when(donationService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/donaciones/1")
                .with(user("adminUser").roles("ADMIN")))
            .andExpect(status().isNoContent());

        verify(donationService).delete(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(donationService.delete(1L)).thenReturn(false);

        mockMvc.perform(delete("/donaciones/1")
                .with(user("adminUser").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
