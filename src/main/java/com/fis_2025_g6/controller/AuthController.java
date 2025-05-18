package com.fis_2025_g6.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.auth.AuthResponse;
import com.fis_2025_g6.auth.JwtUtil;
import com.fis_2025_g6.dto.AuthRequest;
import com.fis_2025_g6.dto.RegisterRequest;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.factory.AdministratorFactory;
import com.fis_2025_g6.factory.AdoptantFactory;
import com.fis_2025_g6.factory.RefugeFactory;
import com.fis_2025_g6.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdoptantFactory adoptantFactory;

    @Autowired
    private RefugeFactory refugeFactory;

    @Autowired
    private AdministratorFactory administratorFactory;

    @Operation(summary = "Iniciar sesión con una cuenta", description = "Cualquier persona sin autenticar")
    @PostMapping("/iniciosesion")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(summary = "Registrarse en el sistema", description = "Cualquier persona sin autenticar")
    @PostMapping("/registro")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest request, BindingResult result) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya está registrado");
        }
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        User newUser;
        switch (request.getType().toUpperCase()) {
            case "ADOPTANTE":
                newUser = adoptantFactory.create(
                    request.getUsername(),
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getPhoneNumber(),
                    request.getAddress()
                );
                break;
            case "REFUGIO":
                newUser = refugeFactory.create(
                    request.getUsername(),
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getPhoneNumber(),
                    request.getAddress()
                );
                break;
            case "ADMIN":
                newUser = administratorFactory.create(
                    request.getUsername(),
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getPhoneNumber(),
                    request.getAddress()
                );
                break;
            default:
                return ResponseEntity.badRequest().body("Tipo de usuario inválido");
        }

        userRepository.save(newUser);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }
}
