package com.fis_2025_g6;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis_2025_g6.controller.FormController;
import com.fis_2025_g6.dto.FormDto;
import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.entity.Form;
import com.fis_2025_g6.service.ApplicationService;
import com.fis_2025_g6.service.FormService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FormControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FormService formService;

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private FormController formController;

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(formController).build();
    }

    @Test
    void testFindAll() throws Exception {
        Form form1 = new Form();
        form1.setId(1L);
        form1.setHousingType("Casa");

        Form form2 = new Form();
        form2.setId(2L);
        form2.setHousingType("Departamento");

        when(formService.findAll()).thenReturn(List.of(form1, form2));

        mockMvc.perform(get("/formularios")
                .with(user("refugeUser").roles("REFUGE"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].housingType").value("Casa"))
            .andExpect(jsonPath("$[1].housingType").value("Departamento"));
    }

    @Test
    void testFindById_Found() throws Exception {
        Form form = new Form();
        form.setId(1L);
        form.setHousingType("Casa");

        when(formService.findById(1L)).thenReturn(Optional.of(form));

        mockMvc.perform(get("/formularios/1")
                .with(user("adoptUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.housingType").value("Casa"));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(formService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/formularios/1")
                .with(user("adoptUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreateForm_Success() throws Exception {
        FormDto dto = new FormDto();
        dto.setApplicationId(1L);
        dto.setHasPets(true);
        dto.setHasRoom(true);
        dto.setHousingType("Casa");
        dto.setHoursAwayFromHome(4);
        dto.setVaccinationCommitment(true);
        dto.setPreviousExperience("Tuve un perro");

        Application application = new Application();
        application.setId(1L);

        Form createdForm = new Form();
        createdForm.setId(1L);
        createdForm.setHousingType("Casa");
        createdForm.setApplication(application);

        when(applicationService.findById(1L)).thenReturn(Optional.of(application));
        when(formService.create(any(Form.class))).thenReturn(createdForm);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/formularios")
                .with(user("adoptUser").roles("ADOPTANT"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/formularios/1"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.housingType").value("Casa"));
    }

    @Test
    void testDeleteForm_Success() throws Exception {
        when(formService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/formularios/1")
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isNoContent());

        verify(formService).delete(1L);
    }

    @Test
    void testDeleteForm_NotFound() throws Exception {
        when(formService.delete(1L)).thenReturn(false);

        mockMvc.perform(delete("/formularios/1")
                .with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound());
    }
}
