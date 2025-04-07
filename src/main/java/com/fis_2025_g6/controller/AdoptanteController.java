package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.AdoptanteDto;
import com.fis_2025_g6.entity.Adoptante;
import com.fis_2025_g6.factory.AdoptanteFactory;
import com.fis_2025_g6.service.AdoptanteService;

@RestController
@RequestMapping("/adoptantes")
public class AdoptanteController {
    private final AdoptanteService adoptanteService;
    private final AdoptanteFactory adoptanteFactory;

    public AdoptanteController(AdoptanteService adoptanteService, AdoptanteFactory adoptanteFactory) {
        this.adoptanteService = adoptanteService;
        this.adoptanteFactory = adoptanteFactory;
    }

    @GetMapping
    public List<Adoptante> findAll() {
        return adoptanteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adoptante> findById(@PathVariable Long id) {
        return adoptanteService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Adoptante> create(@RequestBody AdoptanteDto dto) {
        Adoptante adoptante = (Adoptante)adoptanteFactory.create(
            dto.getNombre(),
            dto.getCorreo(),
            dto.getContrasena(),
            dto.getTelefono(),
            dto.getDireccion()
        );
        Adoptante creado = adoptanteService.create(adoptante);
        return ResponseEntity.created(URI.create("/adoptantes/" + creado.getId())).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = adoptanteService.delete(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
