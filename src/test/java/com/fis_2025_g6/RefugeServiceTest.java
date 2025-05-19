package com.fis_2025_g6;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.repository.RefugeRepository;
import com.fis_2025_g6.service.RefugeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RefugeServiceTest {

    @Mock
    private RefugeRepository refugeRepository;

    @InjectMocks
    private RefugeService refugeService;

    @Test
    void testFindAll() {
        Refuge refuge1 = new Refuge();
        refuge1.setId(1L);
        refuge1.setUsername("refuge1");

        Refuge refuge2 = new Refuge();
        refuge2.setId(2L);
        refuge2.setUsername("refuge2");

        when(refugeRepository.findAll()).thenReturn(List.of(refuge1, refuge2));

        List<Refuge> result = refugeService.findAll();

        assertEquals(2, result.size());
        assertEquals("refuge1", result.get(0).getUsername());
    }

    @Test
    void testFindById() {
        Refuge refuge = new Refuge();
        refuge.setId(1L);
        refuge.setUsername("refuge1");

        when(refugeRepository.findById(1L)).thenReturn(Optional.of(refuge));

        Optional<Refuge> result = refugeService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("refuge1", result.get().getUsername());
    }

    @Test
    void testFindByUsername() {
        Refuge refuge = new Refuge();
        refuge.setId(1L);
        refuge.setUsername("refuge1");

        when(refugeRepository.findByUsername("refuge1")).thenReturn(Optional.of(refuge));

        Optional<Refuge> result = refugeService.findByUsername("refuge1");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testCreate() {
        Refuge refuge = new Refuge();
        refuge.setUsername("newRefuge");

        when(refugeRepository.save(refuge)).thenReturn(refuge);

        Refuge result = refugeService.create(refuge);

        assertNotNull(result);
        assertEquals("newRefuge", result.getUsername());
    }

    @Test
    void testDelete_Exists() {
        Long id = 1L;

        when(refugeRepository.existsById(id)).thenReturn(true);

        boolean deleted = refugeService.delete(id);

        assertTrue(deleted);
        verify(refugeRepository).deleteById(id);
    }

    @Test
    void testDelete_NotExists() {
        Long id = 99L;

        when(refugeRepository.existsById(id)).thenReturn(false);

        boolean deleted = refugeService.delete(id);

        assertFalse(deleted);
        verify(refugeRepository, never()).deleteById(id);
    }
}
