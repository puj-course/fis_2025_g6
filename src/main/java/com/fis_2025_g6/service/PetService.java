package com.fis_2025_g6.service;

import com.fis_2025_g6.AdoptionStatus;
import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.repository.PetRepository;
import com.fis_2025_g6.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public List<Pet> filter(String species, Integer age, String sex, AdoptionStatus status) {
        return petRepository.filter(species, age, sex, status);
    }

    public Refuge findRefugeByUsername(String username) {
        return userRepository.findByUsername(username)
            .filter(Refuge.class::isInstance)
            .map(user -> (Refuge)user)
            .orElseThrow(() -> new RuntimeException("Refugio no encontrado o inválido"));
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    public Pet create(Pet pet) {
        return petRepository.save(pet);
    }

    public boolean delete(Long id) {
        boolean exists = petRepository.existsById(id);
        if (exists) {
            petRepository.deleteById(id);
        }
        return exists;
    }
}
