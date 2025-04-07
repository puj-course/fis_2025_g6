package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Usuario;
import com.fis_2025_g6.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String correo) {
        return usuarioRepository.findByEmail(correo);
    }

    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean delete(Long id) {
        boolean existe = usuarioRepository.existsById(id);
        if (existe) {
            usuarioRepository.deleteById(id);
        }
        return existe;
    }
}
