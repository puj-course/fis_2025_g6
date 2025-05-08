package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.repository.ApplicationRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);
    }

    public Application create(Application application) {
        return applicationRepository.save(application);
    }

    public boolean delete(Long id) {
        boolean exists = applicationRepository.existsById(id);
        if (exists) {
            applicationRepository.deleteById(id);
        }
        return exists;
    }
}
