package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.service.DonationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/donaciones")
public class DonationController {
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public List<Donation> findAll() {
        return donationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> findById(@PathVariable Long id) {
        return donationService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Donation> create(@RequestBody @Valid Donation donation) {
        Donation created = donationService.create(donation);
        return ResponseEntity.created(URI.create("/donaciones/" + created.getId())).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = donationService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
