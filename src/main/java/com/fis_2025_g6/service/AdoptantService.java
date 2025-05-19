package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.repository.AdoptantRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptantService {
    private final AdoptantRepository adoptantRepository;

    public AdoptantService(AdoptantRepository adoptantRepository) {
        this.adoptantRepository = adoptantRepository;
    }

    public List<Adoptant> findAll() {
        return adoptantRepository.findAll();
    }

    public Optional<Adoptant> findById(Long id) {
        return adoptantRepository.findById(id);
    }

    public Optional<Adoptant> findByUsername(String username) {
        return adoptantRepository.findByUsername(username);
    }

    public Adoptant update(Adoptant adoptant) {
        return adoptantRepository.save(adoptant);
    }

    public boolean existsByUsernameAndIdNot(String username, Long id) {
        return adoptantRepository.existsByUsernameAndIdNot(username, id);
    }

    public boolean existsByEmailAndIdNot(String email, Long id) {
        return adoptantRepository.existsByEmailAndIdNot(email, id);
    }

    public Adoptant create(Adoptant adoptant) {
        return adoptantRepository.save(adoptant);
    }

    public boolean delete(Long id) {
        boolean exists = adoptantRepository.existsById(id);
        if (exists) {
            adoptantRepository.deleteById(id);
        }
        return exists;
    }
}
