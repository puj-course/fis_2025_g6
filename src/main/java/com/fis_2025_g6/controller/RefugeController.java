package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.RefugeDto;
import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.factory.RefugeFactory;
import com.fis_2025_g6.service.RefugeService;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Obtener la lista de refugios", description = "Usuarios ADOPTANTE o REFUGIO")
    @GetMapping
    public ResponseEntity<List<Refuge>> findAll() {
        List<Refuge> refuges = refugeService.findAll();
        return ResponseEntity.ok(refuges);
    }

    @Operation(summary = "Obtener un refugio por su ID", description = "Usuarios ADOPTANTE o REFUGIO")
    @GetMapping("/{id}")
    public ResponseEntity<Refuge> findById(@PathVariable Long id) {
        return refugeService.findById(id)
            .map(refuge -> ResponseEntity.ok(refuge))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener la lista de mascotas de un refugio", description = "Usuarios ADOPTANTE o REFUGIO")
    @GetMapping("/{id}/mascotas")
    public ResponseEntity<List<Pet>> findPetsByRefuge(@PathVariable Long id) {
        return refugeService.findById(id)
            .map(refuge -> ResponseEntity.ok(refuge.getPets()))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener la lista de donaciones a un refugio", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @GetMapping("/{id}/donaciones")
    public ResponseEntity<List<Donation>> findDonationsByRefuge(@PathVariable Long id) {
        return refugeService.findById(id)
            .map(refuge -> ResponseEntity.ok(refuge.getReceivedDonations()))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un refugio")
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

    @Operation(summary = "Eliminar un refugio por su ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = refugeService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
