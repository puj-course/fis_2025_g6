package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/solicitudes")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<Application> findAll() {
        return applicationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> findById(@PathVariable Long id) {
        return applicationService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Application> create(@RequestBody @Valid Application application) {
        Application created = applicationService.create(application);
        return ResponseEntity.created(URI.create("/solicitudes/" + created.getId())).body(created);
    }

    @PreAuthorize("hasRole('REFUGIO') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = applicationService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
