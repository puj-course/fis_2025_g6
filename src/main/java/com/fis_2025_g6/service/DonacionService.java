package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Donacion;
import com.fis_2025_g6.repository.DonacionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonacionService {
    private final DonacionRepository donacionRepository;

    public DonacionService(DonacionRepository donacionRepository) {
        this.donacionRepository = donacionRepository;
    }

    public List<Donacion> findAll() {
        return donacionRepository.findAll();
    }

    public Optional<Donacion> findById(Long id) {
        return donacionRepository.findById(id);
    }

    public Donacion create(Donacion donacion) {
        return donacionRepository.save(donacion);
    }

    public boolean delete(Long id) {
        boolean existe = donacionRepository.existsById(id);
        if (existe) {
            donacionRepository.deleteById(id);
        }
        return existe;
    }
}
