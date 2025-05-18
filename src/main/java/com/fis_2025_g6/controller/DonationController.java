package com.fis_2025_g6.controller;

import java.net.URI;
import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.auth.CustomUserDetails;
import com.fis_2025_g6.dto.DonationDto;
import com.fis_2025_g6.entity.Adoptant;
import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.service.DonationService;
import com.fis_2025_g6.service.RefugeService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/donaciones")
public class DonationController {
    private final DonationService donationService;
    private final RefugeService refugeService;

    public DonationController(DonationService donationService, RefugeService refugeService) {
        this.donationService = donationService;
        this.refugeService = refugeService;
    }

    @Operation(summary = "Obtener la lista de donaciones")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Donation>> findAll() {
        List<Donation> donations = donationService.findAll();
        return ResponseEntity.ok(donations);
    }

    @Operation(summary = "Obtener una donación por su ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Donation> findById(@PathVariable Long id) {
        return donationService.findById(id)
            .map(application -> ResponseEntity.ok(application))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una donación", description = "Usuarios ADOPTANTE")
    @PreAuthorize("hasRole('ADOPTANT') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(
        @RequestBody @Valid DonationDto dto,
        @AuthenticationPrincipal CustomUserDetails principal
    ) {
        User user = principal.getUser();
        if (!(user instanceof Adoptant)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo un adoptante puede donar");
        }

        Refuge refuge = refugeService.findByUsername(dto.getRefugeUsername())
            .orElseThrow(() -> new IllegalArgumentException("Refugio no encontrado"));

        Donation donation = new Donation();
        donation.setAmount(dto.getAmount());
        donation.setDate(new Date(System.currentTimeMillis()));
        donation.setRefuge(refuge);
        donation.setAdoptant((Adoptant)user);
        Donation created = donationService.create(donation);
        return ResponseEntity.created(URI.create("/donaciones/" + created.getId())).body(created);
    }

    @Operation(summary = "Eliminar una donación por su ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = donationService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
