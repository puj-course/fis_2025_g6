package com.fis_2025_g6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    // Prueba negativa: eliminación fallida de un adoptante inexistente
    @Test
    void testDeleteAdoptant_NotFound() {
        Long nonExistentId = 999L;
        when(adoptantRepository.existsById(nonExistentId)).thenReturn(false);

        boolean deleted = adoptantService.delete(nonExistentId);

        assertFalse(deleted, "El método debe devolver false si el ID no existe");
    }

    // Prueba de caso borde: nombre vacío de adoptante
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
}
