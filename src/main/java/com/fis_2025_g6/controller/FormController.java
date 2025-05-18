package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.FormDto;
import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.entity.Form;
import com.fis_2025_g6.service.ApplicationService;
import com.fis_2025_g6.service.FormService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/formularios")
public class FormController {
    private final FormService formService;
    private final ApplicationService applicationService;

    public FormController(FormService formService, ApplicationService applicationService) {
        this.formService = formService;
        this.applicationService = applicationService;
    }

    @Operation(summary = "Obtener la lista de formularios", description = "Usuarios REFUGIO")
    @PreAuthorize("hasRole('REFUGE') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Form>> findAll() {
        List<Form> forms = formService.findAll();
        return ResponseEntity.ok(forms);
    }

    @Operation(summary = "Obtener un formulario por su ID", description = "Usuarios ADOPTANTE o REFUGIO")
    @GetMapping("/{id}")
    public ResponseEntity<Form> findById(@PathVariable Long id) {
        return formService.findById(id)
            .map(form -> ResponseEntity.ok(form))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un formulario", description = "Usuarios ADOPTANTE")
    @PreAuthorize("hasRole('ADOPTANT') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Form> create(@RequestBody @Valid FormDto dto) {
        Application application = applicationService.findById(dto.getApplicationId())
            .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        Form form = new Form();
        form.setHasPets(dto.isHasPets());
        form.setHasRoom(dto.isHasRoom());
        form.setHoursAwayFromHome(dto.getHoursAwayFromHome());
        form.setHousingType(dto.getHousingType());
        form.setVaccinationCommitment(dto.isVaccinationCommitment());
        form.setPreviousExperience(dto.getPreviousExperience());
        form.setApplication(application);
    
        Form created = formService.create(form);
        return ResponseEntity.created(URI.create("/formularios/" + created.getId())).body(created);
    }

    @Operation(summary = "Eliminar un formulario por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = formService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
