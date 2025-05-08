package com.fis_2025_g6.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fis_2025_g6.dto.UserDto;
import com.fis_2025_g6.entity.User;
import com.fis_2025_g6.factory.UserFactory;
import com.fis_2025_g6.service.UserService;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    private final UserService userService;
    private final UserFactory userFactory;

    // TODO: Elegir refugio
    public UserController(UserService userService, @Qualifier("adoptantFactory") UserFactory userFactory) {
        this.userService = userService;
        this.userFactory = userFactory;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto dto) {
        User user = userFactory.create(
            dto.getUsername(),
            dto.getEmail(),
            dto.getPassword(),
            dto.getPhoneNumber(),
            dto.getAddress()
        );
        User created = userService.create(user);
        return ResponseEntity.created(URI.create("/usuarios/" + created.getId())).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = userService.delete(id);
        return deleted
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}
