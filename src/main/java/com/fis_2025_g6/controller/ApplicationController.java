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
    @PreAuthorize("hasRole('ADOPTANT') or hasRole('ADMIN')")
    public ResponseEntity<?> create(
        @RequestBody @Valid ApplicationDto dto,
        @AuthenticationPrincipal CustomUserDetails principal
    ) {
        User user = principal.getUser();
        if (!(user instanceof Adoptant)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo un adoptante puede hacer solicitudes");
        }
        Pet pet = petService.findById(dto.getPetId()).get(); // TODO

        Application application = new Application();
        application.setDate(new Date(System.currentTimeMillis()));
        application.setStatusAndSync(ApplicationStatus.PENDING);
        application.setAdoptant((Adoptant)user);
        application.setPet(pet);

        Application created = applicationService.create(application);
        return ResponseEntity.created(URI.create("/solicitudes/" + created.getId())).body(created);
    }

    @PreAuthorize("hasRole('ADOPTANT') or hasRole('REFUGE') or hasRole('ADMIN')")
    @PatchMapping("/{id}")
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
