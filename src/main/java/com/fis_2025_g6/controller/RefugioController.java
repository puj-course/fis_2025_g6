package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.RefugioDto;
import com.fis_2025_g6.entity.Refugio;
import com.fis_2025_g6.factory.RefugioFactory;
import com.fis_2025_g6.service.RefugioService;

@RestController
@RequestMapping("/refugios")
public class RefugioController {
    private final RefugioService refugioService;
    private final RefugioFactory refugioFactory;

    public RefugioController(RefugioService refugioService, RefugioFactory refugioFactory) {
        this.refugioService = refugioService;
        this.refugioFactory = refugioFactory;
    }

    @GetMapping
    public List<Refugio> findAll() {
        return refugioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Refugio> findById(@PathVariable Long id) {
        return refugioService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Refugio> create(@RequestBody RefugioDto dto) {
        Refugio refugio = (Refugio)refugioFactory.create(
            dto.getNombre(),
            dto.getCorreo(),
            dto.getContrasena(),
            dto.getTelefono(),
            dto.getDireccion()
        );
        Refugio creado = refugioService.create(refugio);
        return ResponseEntity.created(URI.create("/refugios/" + creado.getId())).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = refugioService.delete(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
