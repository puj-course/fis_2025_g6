package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Formulario;
import com.fis_2025_g6.repository.FormularioRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormularioService {
    private final FormularioRepository formularioRepository;

    public FormularioService(FormularioRepository formularioRepository) {
        this.formularioRepository = formularioRepository;
    }

    public List<Formulario> findAll() {
        return formularioRepository.findAll();
    }

    public Optional<Formulario> findById(Long id) {
        return formularioRepository.findById(id);
    }

    public Formulario create(Formulario formulario) {
        return formularioRepository.save(formulario);
    }

    public boolean delete(Long id) {
        boolean existe = formularioRepository.existsById(id);
        if (existe) {
            formularioRepository.deleteById(id);
        }
        return existe;
    }
}
