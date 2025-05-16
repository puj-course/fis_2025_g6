package com.fis_2025_g6.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.fis_2025_g6.entity.Administrator;
import com.fis_2025_g6.repository.AdministratorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministratorService {
    private final AdministratorRepository administratorRepository;

    public Optional<Administrator> findById(Long id) {
        return administratorRepository.findById(id);
    }

    public List<Administrator> findAll() {
        return administratorRepository.findAll();
    }

    public Administrator create(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public boolean delete(Long id) {
        boolean exists = administratorRepository.existsById(id);
        if (exists) {
            administratorRepository.deleteById(id);
        }
        return exists;
    }
}
