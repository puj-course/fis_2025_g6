package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.entity.Solicitud;
import com.fis_2025_g6.service.SolicitudService;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {
    private final SolicitudService solicitudAdopcionService;

    public SolicitudController(SolicitudService solicitudAdopcionService) {
        this.solicitudAdopcionService = solicitudAdopcionService;
    }

    @GetMapping
    public List<Solicitud> findAll() {
        return solicitudAdopcionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> findById(@PathVariable Long id) {
        return solicitudAdopcionService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Solicitud> create(@RequestBody Solicitud solicitud) {
        Solicitud creada = solicitudAdopcionService.create(solicitud);
        return ResponseEntity.created(URI.create("/solicitudes/" + creada.getId())).body(creada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = solicitudAdopcionService.delete(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
