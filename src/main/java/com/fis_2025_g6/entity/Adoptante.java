package com.fis_2025_g6.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue("ADOPTANTE")
@NoArgsConstructor
@AllArgsConstructor
public class Adoptante extends Usuario {
    @Column(nullable = false)
    private String nombreAdopt;

    @OneToMany(mappedBy = "adoptante")
    private Set<Donacion> donaciones;

    @OneToMany(mappedBy = "adoptante")
    private Set<Solicitud> solicitudes;
}
