package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.auth.CustomUserDetails;
import com.fis_2025_g6.dto.AdoptantDto;
import com.fis_2025_g6.dto.UpdateAdoptantDto;
import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.entity.User;
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
    public ResponseEntity<List<Adoptant>> findAll() {
        List<Adoptant> adoptants = adoptantService.findAll();
        return ResponseEntity.ok(adoptants);
    }

    @Operation(summary = "Obtener un adoptante por su ID", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Adoptant> findById(@PathVariable Long id) {
        return adoptantService.findById(id)
            .map(adoptant -> ResponseEntity.ok(adoptant))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener el usuario propio")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Adoptant> findCurrentAdoptant(@AuthenticationPrincipal CustomUserDetails principal) {
        User user = principal.getUser();
        return adoptantService.findByUsername(user.getUsername())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener la lista de solicitudes de un adoptante", description = "Usuarios ADOPTANTE")
    @PreAuthorize("hasRole('ADOPTANT') or hasRole('ADMIN')")
    @GetMapping("/{id}/solicitudes")
    public ResponseEntity<List<Application>> findApplications(@PathVariable Long id) {
        if (adoptantService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Application> applications = applicationService.findByAdoptantId(id);
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "Obtener la lista de donaciones de un adoptante", description = "Usuarios ADOPTANTE")
    @PreAuthorize("hasRole('ADOPTANT') or hasRole('ADMIN')")
    @GetMapping("/{id}/donaciones")
    public ResponseEntity<List<Donation>> getDonations(@PathVariable Long id) {
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
        adoptant.setAdoptantName(dto.getAdoptantName());
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

    @Operation(summary = "Actualizar datos del adoptante", description = "Usuarios ADOPTANTE")
    @PreAuthorize("hasRole('ADOPTANT')")
    @PutMapping("/me")
    public ResponseEntity<Adoptant> update(
        @RequestBody UpdateAdoptantDto dto,
        @AuthenticationPrincipal CustomUserDetails principal
    ) {
        Adoptant adoptant = principal.getAsAdoptant().get();
        Long currentId = adoptant.getId();

        if (dto.getUsername() != null) {
            if (adoptantService.existsByUsernameAndIdNot(dto.getUsername(), currentId)) {
                return ResponseEntity.badRequest().body(null);
            }
            adoptant.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null) {
            if (adoptantService.existsByEmailAndIdNot(dto.getEmail(), currentId)) {
                return ResponseEntity.badRequest().body(null);
            }
            adoptant.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null) adoptant.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getAddress() != null) adoptant.setAddress(dto.getAddress());
        if (dto.getAdoptantName() != null) adoptant.setAdoptantName(dto.getAdoptantName());

        Adoptant updated = adoptantService.update(adoptant);
        return ResponseEntity.ok(updated);
    }
}
