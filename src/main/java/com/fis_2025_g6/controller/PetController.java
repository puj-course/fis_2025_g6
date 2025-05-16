package com.fis_2025_g6.controller;

import java.net.URI;
import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.AdoptionStatus;
import com.fis_2025_g6.auth.CustomUserDetails;
import com.fis_2025_g6.dto.PetDto;
import com.fis_2025_g6.entity.Pet;
import com.fis_2025_g6.entity.Refuge;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.service.PetService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/mascotas")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(summary = "Obtener la lista de mascotas", description = "Usuarios ADOPTANTE o REFUGIO")
    @GetMapping
    public List<Pet> findAll() {
        return petService.findAll();
    }

    @Operation(summary = "Obtener una mascota por su ID", description = "Usuarios ADOPTANTE o REFUGIO")
    @GetMapping("/{id}")
    public ResponseEntity<Pet> findById(@PathVariable Long id) {
        return petService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar una mascota por especie, edad, sexo o estado", description = "Usuarios ADOPTANTE o REFUGIO")
    @GetMapping("/filtro")
    public List<Pet> filter(
        @RequestParam(required = false) String species,
        @RequestParam(required = false) Integer age,
        @RequestParam(required = false) String sex,
        @RequestParam(required = false) AdoptionStatus status
    ) {
        return petService.filter(species, age, sex, status);
    }

    @Operation(summary = "Obtener la lista de solicitudes para una mascota", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @GetMapping("/{id}/solicitudes")
    public ResponseEntity<?> findApplicationsByPet(@PathVariable Long id) {
        return petService.findById(id)
            .map(pet -> ResponseEntity.ok(pet.getApplications()))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una mascota", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PetDto dto, @AuthenticationPrincipal CustomUserDetails principal) {
        User user = principal.getUser();
        if (!(user instanceof Refuge)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo un refugio puede crear mascotas");
        }

        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setSpecies(dto.getSpecies());
        pet.setAge(dto.getAge());
        pet.setSex(dto.getSex());
        pet.setDescription(dto.getDescription());
        pet.setRegistrationDate(new Date(System.currentTimeMillis()));
        pet.setStatus(AdoptionStatus.AVAILABLE);
        pet.setRefuge((Refuge)user);
        Pet created = petService.create(pet);
        return ResponseEntity.created(URI.create("/mascotas/" + created.getId())).body(created);
    }

    @Operation(summary = "Eliminar una mascota por su ID", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = petService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
