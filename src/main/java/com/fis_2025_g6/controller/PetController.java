package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.service.PetService;

@RestController
@RequestMapping("/mascotas")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> findAll() {
        return petService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> findById(@PathVariable Long id) {
        return petService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pet> create(@RequestBody Pet pet) {
        Pet created = petService.create(pet);
        return ResponseEntity.created(URI.create("/mascotas/" + created.getId())).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = petService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
