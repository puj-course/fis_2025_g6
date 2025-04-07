package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.entity.Formulario;
import com.fis_2025_g6.service.FormularioService;

@RestController
@RequestMapping("/formularios")
public class FormularioController {
    private final FormularioService formularioService;

    public FormularioController(FormularioService formularioService) {
        this.formularioService = formularioService;
    }

    @GetMapping
    public List<Formulario> findAll() {
        return formularioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formulario> findById(@PathVariable Long id) {
        return formularioService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Formulario> create(@RequestBody Formulario formulario) {
        Formulario creada = formularioService.create(formulario);
        return ResponseEntity.created(URI.create("/formularios/" + creada.getId())).body(creada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = formularioService.delete(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
