package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "usu_tipo", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Long id;

    @Column(name = "usu_nombre", nullable = false, unique = true)
    private String username;

    @Column(name = "usu_correo", nullable = false, unique = true)
    private String email;

    @Column(name = "usu_contrasena", nullable = false)
    private String password;

    @Column(name = "usu_telefono", nullable = false)
    private String phoneNumber;

    @Column(name = "usu_direccion", nullable = false)
    private String address;
}
