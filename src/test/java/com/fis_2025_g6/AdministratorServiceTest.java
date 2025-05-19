package com.fis_2025_g6;

import com.fis_2025_g6.entity.Administrator;
import com.fis_2025_g6.repository.AdministratorRepository;
import com.fis_2025_g6.service.AdministratorService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdministratorServiceTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @InjectMocks
    private AdministratorService administratorService;

    @Test
    void testCreateAdministrator() {
        Administrator admin = new Administrator();
        admin.setUsername("admin1");
        admin.setEmail("admin1@example.com");
        admin.setPassword("adminpass");

        when(administratorRepository.save(any(Administrator.class))).thenReturn(admin);

        Administrator result = administratorService.create(admin);

        assertNotNull(result);
        assertEquals("admin1", result.getUsername());
    }

    @Test
    void testFindAllAdministrators() {
        List<Administrator> mockList = List.of(new Administrator(), new Administrator());
        when(administratorRepository.findAll()).thenReturn(mockList);

        List<Administrator> result = administratorService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size(), "Debe devolver 2 administradores");
    }

    @Test
    void testFindById_Exists() {
        Administrator admin = new Administrator();
        admin.setId(1L);
        when(administratorRepository.findById(1L)).thenReturn(Optional.of(admin));

        Optional<Administrator> result = administratorService.findById(1L);

        assertTrue(result.isPresent(), "Debe encontrar el administrador");
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_NotFound() {
        when(administratorRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Administrator> result = administratorService.findById(999L);

        assertFalse(result.isPresent(), "No debe encontrar el administrador");
    }

    @Test
    void testDeleteAdministrator_Exists() {
        Long id = 1L;
        when(administratorRepository.existsById(id)).thenReturn(true);

        boolean deleted = administratorService.delete(id);

        assertTrue(deleted, "Debe devolver true si el administrador existe");
    }

    @Test
    void testDeleteAdministrator_NotFound() {
        Long id = 999L;
        when(administratorRepository.existsById(id)).thenReturn(false);

        boolean deleted = administratorService.delete(id);

        assertFalse(deleted, "Debe devolver false si el administrador no existe");
    }

    @Test
    void testCreateAdministrator_EmptyName() {
        Administrator admin = new Administrator();
        admin.setUsername("");
        admin.setEmail("empty@example.com");
        admin.setPassword("123456");

        when(administratorRepository.save(admin)).thenReturn(admin);

        Administrator result = administratorService.create(admin);

        assertNotNull(result);
        assertEquals("", result.getUsername(), "Debe permitir guardar un nombre vacío si no hay validación activa");
    }
}
