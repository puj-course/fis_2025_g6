package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Refugio;
import com.fis_2025_g6.repository.RefugioRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RefugioService {
    private final RefugioRepository refugioRepository;

    public RefugioService(RefugioRepository refugioRepository) {
        this.refugioRepository = refugioRepository;
    }

    public List<Refugio> findAll() {
        return refugioRepository.findAll();
    }

    public Optional<Refugio> findById(Long id) {
        return refugioRepository.findById(id);
    }

    public Refugio create(Refugio refugio) {
        return refugioRepository.save(refugio);
    }

    public boolean delete(Long id) {
        boolean existe = refugioRepository.existsById(id);
        if (existe) {
            refugioRepository.deleteById(id);
        }
        return existe;
    }
}
