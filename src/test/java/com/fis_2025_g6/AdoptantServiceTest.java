package com.fis_2025_g6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.repository.AdoptantRepository;
import com.fis_2025_g6.service.AdoptantService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdoptantServiceTest {
    @Mock
    private AdoptantRepository adoptantRepository;

    @InjectMocks
    private AdoptantService adoptantService;

    @Test
    void testCreateAdoptant() {
        Adoptant adoptant = new Adoptant();
        adoptant.setUsername("jose");
        adoptant.setEmail("jose@gmail.com");
        adoptant.setPassword("123789");
        adoptant.setPhoneNumber("1234567890");
        adoptant.setAddress("Carrera 20");
        adoptant.setAdoptantName("jose");

        when(adoptantRepository.save(any(Adoptant.class))).thenReturn(adoptant);

        Adoptant result = adoptantService.create(adoptant);

        assertNotNull(result);
        assertEquals("jose", result.getAdoptantName());
    }

    @Test
    void testDeleteAdoptant_NotFound() {
        Long nonExistentId = 999L;
        when(adoptantRepository.existsById(nonExistentId)).thenReturn(false);

        boolean deleted = adoptantService.delete(nonExistentId);

        assertFalse(deleted, "El método debe devolver false si el ID no existe");
    }

    @Test
    void testCreateAdoptant_EmptyName() {
        Adoptant adoptant = new Adoptant();
        adoptant.setUsername("");
        adoptant.setEmail("limite@test.com");
        adoptant.setPassword("123456");
        adoptant.setPhoneNumber("1234567890");
        adoptant.setAddress("Calle Falsa 123");
        adoptant.setAdoptantName("");
    
        when(adoptantRepository.save(adoptant)).thenReturn(adoptant);
    
        Adoptant result = adoptantService.create(adoptant);
    
        assertNotNull(result);
        assertEquals("", result.getUsername(), "Debe permitir guardar un nombre vacío si no hay validación activa");
    }

    @Test
    void testFindAllAdoptants() {
        List<Adoptant> mockList = List.of(new Adoptant(), new Adoptant());
        when(adoptantRepository.findAll()).thenReturn(mockList);

        List<Adoptant> result = adoptantService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size(), "Debe devolver 2 adoptantes");
    }

    @Test
    void testFindById_Exists() {
        Adoptant adoptant = new Adoptant();
        adoptant.setId(1L);
        when(adoptantRepository.findById(1L)).thenReturn(Optional.of(adoptant));

        Optional<Adoptant> result = adoptantService.findById(1L);

        assertTrue(result.isPresent(), "Debe encontrar el adoptante");
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_NotFound() {
        when(adoptantRepository.findById(123L)).thenReturn(Optional.empty());

        Optional<Adoptant> result = adoptantService.findById(123L);

        assertFalse(result.isPresent(), "No debe encontrar el adoptante");
    }

    @Test
    void testDeleteAdoptant_Exists() {
        Long id = 1L;
        when(adoptantRepository.existsById(id)).thenReturn(true);

        boolean deleted = adoptantService.delete(id);

        assertTrue(deleted, "Debe devolver true si el adoptante existe");
    }
}
