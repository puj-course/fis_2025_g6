package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.UsuarioDto;
import com.fis_2025_g6.entity.Usuario;
import com.fis_2025_g6.factory.UsuarioFactory;
import com.fis_2025_g6.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioFactory usuarioFactory;

    public UsuarioController(UsuarioService usuarioService, UsuarioFactory usuarioFactory) {
        this.usuarioService = usuarioService;
        this.usuarioFactory = usuarioFactory;
    }

    @GetMapping
    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return usuarioService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody UsuarioDto dto) {
        Usuario usuario = usuarioFactory.create(
            dto.getNombre(),
            dto.getCorreo(),
            dto.getContrasena(),
            dto.getTelefono(),
            dto.getDireccion()
        );
        Usuario creado = usuarioService.create(usuario);
        return ResponseEntity.created(URI.create("/usuarios/" + creado.getId())).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = usuarioService.delete(id);
        return eliminado
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}
