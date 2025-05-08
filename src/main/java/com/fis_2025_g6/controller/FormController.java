package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.entity.Form;
import com.fis_2025_g6.service.FormService;

@RestController
@RequestMapping("/formularios")
public class FormController {
    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping
    public List<Form> findAll() {
        return formService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Form> findById(@PathVariable Long id) {
        return formService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Form> create(@RequestBody Form form) {
        Form created = formService.create(form);
        return ResponseEntity.created(URI.create("/formularios/" + created.getId())).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = formService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
