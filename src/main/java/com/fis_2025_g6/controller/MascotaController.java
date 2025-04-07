package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.entity.Mascota;
import com.fis_2025_g6.service.MascotaService;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {
    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public List<Mascota> findAll() {
        return mascotaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> findById(@PathVariable Long id) {
        return mascotaService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mascota> create(@RequestBody Mascota mascota) {
        Mascota creada = mascotaService.create(mascota);
        return ResponseEntity.created(URI.create("/mascotas/" + creada.getId())).body(creada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = mascotaService.delete(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
