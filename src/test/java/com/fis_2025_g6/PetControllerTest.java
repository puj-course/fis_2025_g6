package com.fis_2025_g6;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis_2025_g6.controller.PetController;
import com.fis_2025_g6.dto.PetDto;
import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.auth.CustomUserDetails;
import com.fis_2025_g6.service.PetService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

public class PetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void testFindAll() throws Exception {
        Pet p1 = new Pet();
        p1.setId(1L);
        p1.setName("Firulais");

        Pet p2 = new Pet();
        p2.setId(2L);
        p2.setName("Mishi");

        when(petService.findAll()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/mascotas")
                .with(user("adoptUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].name").value("Firulais"))
            .andExpect(jsonPath("$[1].name").value("Mishi"));
    }

    @Test
    void testFindById_Found() throws Exception {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Firulais");

        when(petService.findById(1L)).thenReturn(Optional.of(pet));

        mockMvc.perform(get("/mascotas/1")
                .with(user("refugeUser").roles("REFUGE"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Firulais"));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(petService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/mascotas/1")
                .with(user("refugeUser").roles("REFUGE"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testFilter() throws Exception {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setSpecies("Perro");
        pet.setAge(3);
        pet.setSex("Macho");
        pet.setStatus(AdoptionStatus.AVAILABLE);

        when(petService.filter("Perro", 3, "Macho", AdoptionStatus.AVAILABLE)).thenReturn(List.of(pet));

        mockMvc.perform(get("/mascotas/filtro")
                .param("species", "Perro")
                .param("age", "3")
                .param("sex", "Macho")
                .param("status", "AVAILABLE")
                .with(user("adoptUser").roles("ADOPTANT"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].species").value("Perro"))
            .andExpect(jsonPath("$[0].age").value(3))
            .andExpect(jsonPath("$[0].sex").value("Macho"))
            .andExpect(jsonPath("$[0].status").value("AVAILABLE"));
    }

    @Test
    void testFindApplicationsByPet_Found() throws Exception {
        Pet pet = new Pet();
        pet.setId(1L);

        Application app1 = new Application();
        Application app2 = new Application();

        pet.setApplications(List.of(app1, app2));

        when(petService.findById(1L)).thenReturn(Optional.of(pet));

        mockMvc.perform(get("/mascotas/1/solicitudes")
                .with(user("refugeUser").roles("REFUGE"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testFindApplicationsByPet_NotFound() throws Exception {
        when(petService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/mascotas/1/solicitudes")
                .with(user("refugeUser").roles("REFUGE"))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreate_Success() throws Exception {
        PetDto petDto = new PetDto();
        petDto.setName("Bobi");
        petDto.setSpecies("Perro");
        petDto.setAge(2);
        petDto.setSex("Macho");
        petDto.setDescription("Un perro muy cariñoso");

        Pet createdPet = new Pet();
        createdPet.setId(1L);
        createdPet.setName("Bobi");
        createdPet.setSpecies("Perro");
        createdPet.setAge(2);
        createdPet.setSex("Macho");
        createdPet.setDescription("Un perro muy cariñoso");
        createdPet.setStatus(AdoptionStatus.AVAILABLE);

        Refuge refuge = new Refuge();
        refuge.setId(1L);

        PetController controllerSpy = spy(petController);

        doAnswer(invocation -> {
            PetDto dto = invocation.getArgument(0);

            Pet pet = new Pet();
            pet.setName(dto.getName());
            pet.setSpecies(dto.getSpecies());
            pet.setAge(dto.getAge());
            pet.setSex(dto.getSex());
            pet.setDescription(dto.getDescription());
            pet.setRegistrationDate(new Date(System.currentTimeMillis()));
            pet.setStatus(AdoptionStatus.AVAILABLE);
            pet.setRefuge(refuge);

            Pet created = petService.create(pet);
            return ResponseEntity.created(URI.create("/mascotas/" + created.getId())).body(created);
        }).when(controllerSpy).create(any(PetDto.class), any(CustomUserDetails.class));

        when(petService.create(any(Pet.class))).thenReturn(createdPet);

        mockMvc = MockMvcBuilders.standaloneSetup(controllerSpy).build();

        ObjectMapper objectMapper = new ObjectMapper();
        String petDtoJson = objectMapper.writeValueAsString(petDto);

        mockMvc.perform(post("/mascotas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(petDtoJson)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/mascotas/1"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Bobi"))
            .andExpect(jsonPath("$.species").value("Perro"))
            .andExpect(jsonPath("$.age").value(2))
            .andExpect(jsonPath("$.sex").value("Macho"))
            .andExpect(jsonPath("$.description").value("Un perro muy cariñoso"))
            .andExpect(jsonPath("$.status").value("AVAILABLE"));
        verify(controllerSpy).create(any(PetDto.class), any(CustomUserDetails.class));
        verify(petService).create(any(Pet.class));
    }

    @Test
    void testDelete_Found() throws Exception {
        when(petService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/mascotas/1")
                .with(user("refugeUser").roles("REFUGE")))
            .andExpect(status().isNoContent());

        verify(petService).delete(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(petService.delete(1L)).thenReturn(false);

        mockMvc.perform(delete("/mascotas/1")
                .with(user("refugeUser").roles("REFUGE")))
            .andExpect(status().isNotFound());
    }
}
