package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.auth.CustomUserDetails;
import com.fis_2025_g6.dto.RefugeDto;
import com.fis_2025_g6.dto.UpdateRefugeDto;
import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.entity.User;
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

    @Operation(summary = "Obtener el usuario propio")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Refuge> findCurrentUser(@AuthenticationPrincipal CustomUserDetails principal) {
        User user = principal.getUser();
        return refugeService.findByUsername(user.getUsername())
            .map(ResponseEntity::ok)
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
        refuge.setRefugeName(dto.getRefugeName());
        refuge.setDescription(dto.getDescription());
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

    @Operation(summary = "Actualizar datos del refugio", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE')")
    @PutMapping("/me")
    public ResponseEntity<Refuge> update(
        @RequestBody UpdateRefugeDto dto,
        @AuthenticationPrincipal CustomUserDetails principal
    ) {
        Refuge refuge = principal.getAsRefuge().get();
        Long currentId = refuge.getId();

        if (dto.getUsername() != null) {
            if (refugeService.existsByUsernameAndIdNot(dto.getUsername(), currentId)) {
                return ResponseEntity.badRequest().body(null);
            }
            refuge.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null) {
            if (refugeService.existsByEmailAndIdNot(dto.getEmail(), currentId)) {
                return ResponseEntity.badRequest().body(null);
            }
            refuge.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null) refuge.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getAddress() != null) refuge.setAddress(dto.getAddress());
        if (dto.getRefugeName() != null) refuge.setRefugeName(dto.getRefugeName());
        if (dto.getDescription() != null) refuge.setDescription(dto.getDescription());

        Refuge updated = refugeService.update(refuge);
        return ResponseEntity.ok(updated);
    }
}
