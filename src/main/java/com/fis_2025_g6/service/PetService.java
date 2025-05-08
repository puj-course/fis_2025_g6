package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.repository.PetRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
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
