package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.repository.RefugeRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RefugeService {
    private final RefugeRepository refugeRepository;

    public RefugeService(RefugeRepository refugeRepository) {
        this.refugeRepository = refugeRepository;
    }

    public List<Refuge> findAll() {
        return refugeRepository.findAll();
    }

    public Optional<Refuge> findById(Long id) {
        return refugeRepository.findById(id);
    }

    public Optional<Refuge> findByUsername(String userame) {
        return refugeRepository.findByUsername(userame);
    }

    public Refuge update(Refuge refuge) {
        return refugeRepository.save(refuge);
    }

    public boolean existsByUsernameAndIdNot(String username, Long id) {
        return refugeRepository.existsByUsernameAndIdNot(username, id);
    }

    public boolean existsByEmailAndIdNot(String email, Long id) {
        return refugeRepository.existsByEmailAndIdNot(email, id);
    }

    public Refuge create(Refuge refuge) {
        return refugeRepository.save(refuge);
    }

    public boolean delete(Long id) {
        boolean exists = refugeRepository.existsById(id);
        if (exists) {
            refugeRepository.deleteById(id);
        }
        return exists;
    }
}
