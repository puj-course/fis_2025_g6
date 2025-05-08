package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.service.DonationService;

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
    public ResponseEntity<Donation> create(@RequestBody Donation donation) {
        Donation created = donationService.create(donation);
        return ResponseEntity.created(URI.create("/donaciones/" + created.getId())).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = donationService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
