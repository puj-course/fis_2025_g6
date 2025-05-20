package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.AdministratorDto;
import com.fis_2025_g6.entity.Administrator;
import com.fis_2025_g6.factory.AdministratorFactory;
import com.fis_2025_g6.service.AdministratorService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admins")
public class AdministratorController {
    private final AdministratorService administratorService;
    private final AdministratorFactory administratorFactory;

    public AdministratorController(AdministratorService adoptantService, AdministratorFactory adoptantFactory) {
        this.administratorService = adoptantService;
        this.administratorFactory = adoptantFactory;
    }

    @Operation(summary = "Obtener la lista de administradores")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Administrator>> findAll() {
        List<Administrator> admins = administratorService.findAll();
        return ResponseEntity.ok(admins);
    }

    @Operation(summary = "Obtener un administrador por su ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Administrator> findById(@PathVariable Long id) {
        return administratorService.findById(id)
            .map(admin -> ResponseEntity.ok(admin))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un administrador")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Administrator> create(@RequestBody @Valid AdministratorDto dto) {
        Administrator administrator = (Administrator)administratorFactory.create(
            dto.getUsername(),
            dto.getEmail(),
            dto.getPassword(),
            dto.getPhoneNumber(),
            dto.getAddress()
        );
        Administrator created = administratorService.create(administrator);
        return ResponseEntity.created(URI.create("/admins/" + created.getId())).body(created);
    }

    @Operation(summary = "Eliminar un administrador por su ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = administratorService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
