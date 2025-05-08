package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Form;
import com.fis_2025_g6.repository.FormRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {
    private final FormRepository formRepository;

    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public List<Form> findAll() {
        return formRepository.findAll();
    }

    public Optional<Form> findById(Long id) {
        return formRepository.findById(id);
    }

    public Form create(Form form) {
        return formRepository.save(form);
    }

    public boolean delete(Long id) {
        boolean exists = formRepository.existsById(id);
        if (exists) {
            formRepository.deleteById(id);
        }
        return exists;
    }
}
