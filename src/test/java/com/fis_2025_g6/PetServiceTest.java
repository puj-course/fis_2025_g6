package com.fis_2025_g6;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.repository.PetRepository;
import com.fis_2025_g6.repository.UserRepository;
import com.fis_2025_g6.service.PetService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void testFindById() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fido");
        pet.setSpecies("Dog");
        pet.setAge(4);
        pet.setSex("M");
        pet.setStatus(AdoptionStatus.AVAILABLE);
        pet.setRegistrationDate(new Date(System.currentTimeMillis()));

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Optional<Pet> result = petService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Fido", result.get().getName());
    }

    @Test
    void testFindAll() {
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Fido");
        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Whiskers");

        when(petRepository.findAll()).thenReturn(List.of(pet1, pet2));

        List<Pet> pets = petService.findAll();

        assertEquals(2, pets.size());
        assertEquals("Whiskers", pets.get(1).getName());
    }

    @Test
    void testFilter() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setSpecies("Dog");
        pet.setAge(3);
        pet.setSex("F");
        pet.setStatus(AdoptionStatus.AVAILABLE);

        when(petRepository.filter("Dog", 3, "F", AdoptionStatus.AVAILABLE))
            .thenReturn(List.of(pet));

        List<Pet> filtered = petService.filter("Dog", 3, "F", AdoptionStatus.AVAILABLE);

        assertEquals(1, filtered.size());
        assertEquals("Dog", filtered.get(0).getSpecies());
    }

    @Test
    void testFindRefugeByUsername_Success() {
        Refuge refuge = new Refuge();
        refuge.setId(1L);
        refuge.setUsername("refugeUser");

        when(userRepository.findByUsername("refugeUser")).thenReturn(Optional.of(refuge));

        Refuge found = petService.findRefugeByUsername("refugeUser");

        assertNotNull(found);
        assertEquals("refugeUser", found.getUsername());
    }

    @Test
    void testFindRefugeByUsername_NotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> petService.findRefugeByUsername("unknown"));

        assertEquals("Refugio no encontrado o inválido", ex.getMessage());
    }

    @Test
    void testCreate() {
        Pet pet = new Pet();
        pet.setName("Buddy");

        when(petRepository.save(pet)).thenReturn(pet);

        Pet saved = petService.create(pet);

        assertNotNull(saved);
        assertEquals("Buddy", saved.getName());
    }

    @Test
    void testDelete_Exists() {
        Long id = 1L;
        when(petRepository.existsById(id)).thenReturn(true);

        boolean deleted = petService.delete(id);

        assertTrue(deleted);
        verify(petRepository).deleteById(id);
    }

    @Test
    void testDelete_NotExists() {
        Long id = 99L;
        when(petRepository.existsById(id)).thenReturn(false);

        boolean deleted = petService.delete(id);

        assertFalse(deleted);
        verify(petRepository, never()).deleteById(id);
    }
}
