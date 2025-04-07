package com.fis_2025_g6.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue("REFUGIO")
@NoArgsConstructor
@AllArgsConstructor
public class Refugio extends Usuario {
    @Column(nullable = false)
    private String nombreRefg;

    @Column(nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "refugio")
    private Set<Mascota> mascotas;

    @OneToMany(mappedBy = "refugio")
    private Set<Donacion> donacionesRecb;
}
