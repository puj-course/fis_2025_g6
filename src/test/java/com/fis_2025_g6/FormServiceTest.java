package com.fis_2025_g6;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.fis_2025_g6.entity.Form;
import com.fis_2025_g6.repository.FormRepository;
import com.fis_2025_g6.service.FormService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FormServiceTest {

    @Mock
    private FormRepository formRepository;

    @InjectMocks
    private FormService formService;

    @Test
    void testFindById() {
        Form form = new Form();
        form.setId(1L);
        form.setHasPets(true);
        form.setHousingType("Apartment");
        form.setHoursAwayFromHome(8);
        form.setVaccinationCommitment(true);
        form.setPreviousExperience("Had dogs before");

        when(formRepository.findById(1L)).thenReturn(Optional.of(form));

        Optional<Form> result = formService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Apartment", result.get().getHousingType());
        assertTrue(result.get().isHasPets());
    }

    @Test
    void testFindAll() {
        Form form1 = new Form();
        form1.setId(1L);
        form1.setHousingType("House");

        Form form2 = new Form();
        form2.setId(2L);
        form2.setHousingType("Apartment");

        when(formRepository.findAll()).thenReturn(List.of(form1, form2));

        List<Form> result = formService.findAll();

        assertEquals(2, result.size());
        assertEquals("House", result.get(0).getHousingType());
    }

    @Test
    void testCreate() {
        Form form = new Form();
        form.setHousingType("Condo");

        when(formRepository.save(form)).thenReturn(form);

        Form result = formService.create(form);

        assertNotNull(result);
        assertEquals("Condo", result.getHousingType());
    }

    @Test
    void testDelete_Exists() {
        Long id = 1L;

        when(formRepository.existsById(id)).thenReturn(true);

        boolean deleted = formService.delete(id);

        assertTrue(deleted);
        verify(formRepository).deleteById(id);
    }

    @Test
    void testDelete_NotExists() {
        Long id = 99L;

        when(formRepository.existsById(id)).thenReturn(false);

        boolean deleted = formService.delete(id);

        assertFalse(deleted);
        verify(formRepository, never()).deleteById(id);
    }
}
