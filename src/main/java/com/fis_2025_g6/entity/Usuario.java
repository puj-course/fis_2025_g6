package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "usuario_tipo", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String direccion;
}
