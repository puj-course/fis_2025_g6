package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.AdoptantDto;
import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.factory.AdoptantFactory;
import com.fis_2025_g6.service.AdoptantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/adoptantes")
public class AdoptantController {
    private final AdoptantService adoptantService;
    private final AdoptantFactory adoptantFactory;

    public AdoptantController(AdoptantService adoptantService, AdoptantFactory adoptantFactory) {
        this.adoptantService = adoptantService;
        this.adoptantFactory = adoptantFactory;
    }

    @GetMapping
    public List<Adoptant> findAll() {
        return adoptantService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adoptant> findById(@PathVariable Long id) {
        return adoptantService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Adoptant> create(@RequestBody @Valid AdoptantDto dto) {
        Adoptant adoptant = (Adoptant)adoptantFactory.create(
            dto.getUsername(),
            dto.getEmail(),
            dto.getPassword(),
            dto.getPhoneNumber(),
            dto.getAddress()
        );
        Adoptant created = adoptantService.create(adoptant);
        return ResponseEntity.created(URI.create("/adoptantes/" + created.getId())).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = adoptantService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
