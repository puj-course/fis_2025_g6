package com.fis_2025_g6.service;

import com.fis_2025_g6.ApplicationStatus;
import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.repository.ApplicationRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    private final String notFoundMessage = "Solicitud no encontrada";

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);
    }

    public List<Application> findByAdoptantId(Long adoptantId) {
        return applicationRepository.findByAdoptantId(adoptantId);
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

    public Application approve(Long id) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(notFoundMessage));
        application.approve();
        return applicationRepository.save(application);
    }

    public Application reject(Long id) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(notFoundMessage));
        application.reject();
        return applicationRepository.save(application);
    }

    public Application cancel(Long id) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(notFoundMessage));
        application.cancel();
        return applicationRepository.save(application);
    }

    public Application updateStatus(Long id, ApplicationStatus newStatus) {
        String invalidMessage = "Estado no soportado o transición inválida";
        if (newStatus == null) {
            throw new IllegalArgumentException(invalidMessage);
        }
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(notFoundMessage));
        switch (newStatus) {
            case APPROVED -> application.approve();
            case REJECTED -> application.reject();
            case CANCELED -> application.cancel();
            default -> throw new IllegalArgumentException(invalidMessage);
        }
        return applicationRepository.save(application);
    }
}
