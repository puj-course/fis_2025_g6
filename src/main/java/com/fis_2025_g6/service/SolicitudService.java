package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Solicitud;
import com.fis_2025_g6.repository.SolicitudRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudService {
    private final SolicitudRepository solicitudAdopcionRepository;

    public SolicitudService(SolicitudRepository solicitudAdopcionRepository) {
        this.solicitudAdopcionRepository = solicitudAdopcionRepository;
    }

    public List<Solicitud> findAll() {
        return solicitudAdopcionRepository.findAll();
    }

    public Optional<Solicitud> findById(Long id) {
        return solicitudAdopcionRepository.findById(id);
    }

    public Solicitud create(Solicitud solicitud) {
        return solicitudAdopcionRepository.save(solicitud);
    }

    public boolean delete(Long id) {
        boolean existe = solicitudAdopcionRepository.existsById(id);
        if (existe) {
            solicitudAdopcionRepository.deleteById(id);
        }
        return existe;
    }
}
