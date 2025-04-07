package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Mascota;
import com.fis_2025_g6.repository.MascotaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {
    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<Mascota> findAll() {
        return mascotaRepository.findAll();
    }

    public Optional<Mascota> findById(Long id) {
        return mascotaRepository.findById(id);
    }

    public Mascota create(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public boolean delete(Long id) {
        boolean existe = mascotaRepository.existsById(id);
        if (existe) {
            mascotaRepository.deleteById(id);
        }
        return existe;
    }
}
