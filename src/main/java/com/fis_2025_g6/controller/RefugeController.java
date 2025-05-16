package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.RefugeDto;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.factory.RefugeFactory;
import com.fis_2025_g6.service.RefugeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/refugios")
public class RefugeController {
    private final RefugeService refugeService;
    private final RefugeFactory refugeFactory;

    public RefugeController(RefugeService refugeService, RefugeFactory refugeFactory) {
        this.refugeService = refugeService;
        this.refugeFactory = refugeFactory;
    }

    @GetMapping
    public List<Refuge> findAll() {
        return refugeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Refuge> findById(@PathVariable Long id) {
        return refugeService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Refuge> create(@RequestBody @Valid RefugeDto dto) {
        Refuge refuge = (Refuge)refugeFactory.create(
            dto.getUsername(),
            dto.getEmail(),
            dto.getPassword(),
            dto.getPhoneNumber(),
            dto.getAddress()
        );
        Refuge created = refugeService.create(refuge);
        return ResponseEntity.created(URI.create("/refugios/" + created.getId())).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = refugeService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
