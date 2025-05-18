package com.fis_2025_g6.controller;

import java.net.URI;
import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.ApplicationStatus;
import com.fis_2025_g6.auth.CustomUserDetails;
import com.fis_2025_g6.dto.ApplicationDto;
import com.fis_2025_g6.dto.UpdateApplicationStatusDto;
import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.service.ApplicationService;
import com.fis_2025_g6.service.PetService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/solicitudes")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final PetService petService;

    public ApplicationController(ApplicationService applicationService, PetService petService) {
        this.applicationService = applicationService;
        this.petService = petService;
    }

    @Operation(summary = "Obtener la lista de solicitudes", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Application>> findAll() {
        List<Application> applications = applicationService.findAll();
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "Obtener una solicitud por su ID", description = "Usuarios ADOPTANTE o REFUGIO")
    @GetMapping("/{id}")
    public ResponseEntity<Application> findById(@PathVariable Long id) {
        return applicationService.findById(id)
            .map(application -> ResponseEntity.ok(application))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una solicitud", description = "Usuarios ADOPTANTE")
    @PreAuthorize("hasRole('ADOPTANT') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Application> create(
        @RequestBody @Valid ApplicationDto dto,
        @AuthenticationPrincipal CustomUserDetails principal
    ) {
        User user = principal.getUser();
        if (!(user instanceof Adoptant)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Pet pet = petService.findById(dto.getPetId())
            .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        Application application = new Application();
        application.setDate(new Date(System.currentTimeMillis()));
        application.setStatusAndSync(ApplicationStatus.PENDING);
        application.setAdoptant((Adoptant)user);
        application.setPet(pet);

        Application created = applicationService.create(application);
        return ResponseEntity.created(URI.create("/solicitudes/" + created.getId())).body(created);
    }

    @Operation(summary = "Eliminar una solicitud por su ID", description = "Usuarios ADOPTANTE o REFUGIO")
    @PreAuthorize("hasRole('ADOPTANT') or hasRole('REFUGE') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Application> updateStatus(@PathVariable Long id, @RequestBody UpdateApplicationStatusDto dto) {
        try {
            Application updated = applicationService.updateStatus(id, dto.getStatus());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
