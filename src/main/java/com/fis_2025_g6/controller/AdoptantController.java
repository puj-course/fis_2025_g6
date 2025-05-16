package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.AdoptantDto;
import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.factory.AdoptantFactory;
import com.fis_2025_g6.service.AdoptantService;
import com.fis_2025_g6.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/adoptantes")
public class AdoptantController {
    private final AdoptantService adoptantService;
    private final ApplicationService applicationService;
    private final AdoptantFactory adoptantFactory;

    public AdoptantController(
        AdoptantService adoptantService,
        ApplicationService applicationService,
        AdoptantFactory adoptantFactory
    ) {
        this.adoptantService = adoptantService;
        this.applicationService = applicationService;
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

    @PreAuthorize("hasRole('ADOPTANT') or hasRole('ADMIN')")
    @GetMapping("/{id}/solicitudes")
    public ResponseEntity<?> getApplications(@PathVariable Long id) {
        if (adoptantService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Application> applications = applicationService.findByAdoptantId(id);
        return ResponseEntity.ok(applications);
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
