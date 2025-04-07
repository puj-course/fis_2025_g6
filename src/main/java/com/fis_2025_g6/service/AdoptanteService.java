package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Adoptante;
import com.fis_2025_g6.repository.AdoptanteRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptanteService {
    private final AdoptanteRepository adoptanteRepository;

    public AdoptanteService(AdoptanteRepository adoptanteRepository) {
        this.adoptanteRepository = adoptanteRepository;
    }

    public List<Adoptante> findAll() {
        return adoptanteRepository.findAll();
    }

    public Optional<Adoptante> findById(Long id) {
        return adoptanteRepository.findById(id);
    }

    public Adoptante create(Adoptante adoptante) {
        return adoptanteRepository.save(adoptante);
    }

    public boolean delete(Long id) {
        boolean existe = adoptanteRepository.existsById(id);
        if (existe) {
            adoptanteRepository.deleteById(id);
        }
        return existe;
    }
}
