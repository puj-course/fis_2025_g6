package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.AdoptantDto;
import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.factory.AdoptantFactory;
import com.fis_2025_g6.service.AdoptantService;
import com.fis_2025_g6.service.ApplicationService;
import com.fis_2025_g6.service.DonationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/adoptantes")
public class AdoptantController {
    private final AdoptantService adoptantService;
    private final ApplicationService applicationService;
    private final DonationService donationService;
    private final AdoptantFactory adoptantFactory;

    public AdoptantController(
        AdoptantService adoptantService,
        ApplicationService applicationService,
        DonationService donationService,
        AdoptantFactory adoptantFactory
    ) {
        this.adoptantService = adoptantService;
        this.applicationService = applicationService;
        this.donationService = donationService;
        this.adoptantFactory = adoptantFactory;
    }

    @Operation(summary = "Obtener la lista de adoptantes", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @GetMapping
    public List<Adoptant> findAll() {
        return adoptantService.findAll();
    }

    @Operation(summary = "Obtener un adoptante por su ID", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Adoptant> findById(@PathVariable Long id) {
        return adoptantService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener la lista de solicitudes de un adoptante", description = "Usuarios ADOPTANTE")
    @PreAuthorize("hasRole('ADOPTANT') or hasRole('ADMIN')")
    @GetMapping("/{id}/solicitudes")
    public ResponseEntity<?> getApplications(@PathVariable Long id) {
        if (adoptantService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Application> applications = applicationService.findByAdoptantId(id);
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "Obtener la lista de donaciones de un adoptante", description = "Usuarios ADOPTANTE")
    @PreAuthorize("hasRole('ADOPTANT') or hasRole('ADMIN')")
    @GetMapping("/{id}/donaciones")
    public ResponseEntity<?> getDonations(@PathVariable Long id) {
        if (adoptantService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Donation> donations = donationService.findByAdoptantId(id);
        return ResponseEntity.ok(donations);
    }

    @Operation(summary = "Crear un adoptante")
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

    @Operation(summary = "Eliminar un adoptante por su ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = adoptantService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
