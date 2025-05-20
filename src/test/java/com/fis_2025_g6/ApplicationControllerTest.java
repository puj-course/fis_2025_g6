package com.fis_2025_g6;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis_2025_g6.auth.CustomUserDetails;
import com.fis_2025_g6.controller.ApplicationController;
import com.fis_2025_g6.dto.ApplicationDto;
import com.fis_2025_g6.dto.UpdateApplicationStatusDto;
import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.service.ApplicationService;
import com.fis_2025_g6.service.PetService;

import static org.mockito.Mockito.*;
import java.net.URI;
import java.sql.Date;
import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ApplicationService applicationService;

    @Mock
    private PetService petService;

    @InjectMocks
    private ApplicationController applicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();
    }

    @Test
    @WithMockUser(roles = {"REFUGE"})
    void testFindAll() throws Exception {
        Application app1 = new Application();
        app1.setId(1L);
        Application app2 = new Application();
        app2.setId(2L);

        when(applicationService.findAll()).thenReturn(List.of(app1, app2));

        mockMvc.perform(get("/solicitudes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testFindById_Found() throws Exception {
        Application app = new Application();
        app.setId(1L);

        when(applicationService.findById(1L)).thenReturn(Optional.of(app));

        mockMvc.perform(get("/solicitudes/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(applicationService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/solicitudes/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate_Success() throws Exception {
        ApplicationDto dto = new ApplicationDto();
        dto.setPetId(1L);

        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Firulais");

        Adoptant adoptant = new Adoptant();
        adoptant.setId(1L);

        Application createdApplication = new Application();
        createdApplication.setId(1L);
        createdApplication.setStatus(ApplicationStatus.PENDING);
        createdApplication.setAdoptant(adoptant);
        createdApplication.setPet(pet);
        createdApplication.setDate(new Date(System.currentTimeMillis()));

        ApplicationController controllerSpy = spy(applicationController);

        doAnswer(invocation -> {
            ApplicationDto appDto = invocation.getArgument(0);

            Pet foundPet = petService.findById(appDto.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));
                
            Application application = new Application();
            application.setDate(new Date(System.currentTimeMillis()));
            application.setStatusAndSync(ApplicationStatus.PENDING);
            application.setAdoptant(adoptant);
            application.setPet(foundPet);

            Application created = applicationService.create(application);
            return ResponseEntity.created(URI.create("/solicitudes/" + created.getId())).body(created);
        }).when(controllerSpy).create(any(ApplicationDto.class), any(CustomUserDetails.class));

        when(petService.findById(1L)).thenReturn(Optional.of(pet));
        when(applicationService.create(any(Application.class))).thenReturn(createdApplication);

        mockMvc = MockMvcBuilders.standaloneSetup(controllerSpy).build();

        ObjectMapper mapper = new ObjectMapper();
        String dtoJson = mapper.writeValueAsString(dto);

        mockMvc.perform(post("/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJson)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/solicitudes/1"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.status").value("PENDING"))
            .andExpect(jsonPath("$.pet.id").value(1))
            .andExpect(jsonPath("$.pet.name").value("Firulais"))
            .andExpect(jsonPath("$.adoptant.id").value(1));

        verify(controllerSpy).create(any(ApplicationDto.class), any(CustomUserDetails.class));

        verify(petService).findById(1L);
        verify(applicationService).create(any(Application.class));
    }

    @Test
    void testCreate_Forbidden() throws Exception {
        ApplicationDto dto = new ApplicationDto();
        dto.setPetId(1L);

        User nonAdoptant = new User(); // no es instancia de Adoptant
        CustomUserDetails userDetails = new CustomUserDetails(nonAdoptant);

        ObjectMapper mapper = new ObjectMapper();
        String dtoJson = mapper.writeValueAsString(dto);

        mockMvc.perform(post("/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)
                        .principal(() -> "someUser")
                        .requestAttr("org.springframework.security.core.Authentication", userDetails))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"REFUGE"})
    void testUpdateStatus_Success() throws Exception {
        Application app = new Application();
        app.setId(1L);
        app.setStatusAndSync(ApplicationStatus.APPROVED);

        UpdateApplicationStatusDto dto = new UpdateApplicationStatusDto();
        dto.setStatus(ApplicationStatus.APPROVED);

        when(applicationService.updateStatus(1L, ApplicationStatus.APPROVED)).thenReturn(app);

        ObjectMapper mapper = new ObjectMapper();
        String dtoJson = mapper.writeValueAsString(dto);

        mockMvc.perform(put("/solicitudes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJson)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    @WithMockUser(roles = {"REFUGE"})
    void testUpdateStatus_NotFound() throws Exception {
        UpdateApplicationStatusDto dto = new UpdateApplicationStatusDto();
        dto.setStatus(ApplicationStatus.REJECTED);

        when(applicationService.updateStatus(1L, ApplicationStatus.REJECTED))
                .thenThrow(new jakarta.persistence.EntityNotFoundException());

        ObjectMapper mapper = new ObjectMapper();
        String dtoJson = mapper.writeValueAsString(dto);

        mockMvc.perform(put("/solicitudes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"REFUGE"})
    void testUpdateStatus_BadRequest() throws Exception {
        UpdateApplicationStatusDto dto = new UpdateApplicationStatusDto();
        dto.setStatus(null); // Status inválido

        when(applicationService.updateStatus(1L, null))
                .thenThrow(new IllegalArgumentException());

        ObjectMapper mapper = new ObjectMapper();
        String dtoJson = mapper.writeValueAsString(dto);

        mockMvc.perform(put("/solicitudes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isBadRequest());
    }
}
