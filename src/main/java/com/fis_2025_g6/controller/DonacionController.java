package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.entity.Donacion;
import com.fis_2025_g6.service.DonacionService;

@RestController
@RequestMapping("/donaciones")
public class DonacionController {
    private final DonacionService donacionService;

    public DonacionController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    @GetMapping
    public List<Donacion> findAll() {
        return donacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donacion> findById(@PathVariable Long id) {
        return donacionService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Donacion> create(@RequestBody Donacion donacion) {
        Donacion creada = donacionService.create(donacion);
        return ResponseEntity.created(URI.create("/donaciones/" + creada.getId())).body(creada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = donacionService.delete(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
