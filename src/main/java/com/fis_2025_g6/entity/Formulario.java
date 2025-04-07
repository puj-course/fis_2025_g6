package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Formulario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean tieneMascotas;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean tieneEspacio;

    @Column(nullable = false)
    private String tipoVivienda;

    @Column(nullable = false)
    private Integer horasFuera;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean comprVacuna;

    @Column(nullable = false)
    private String expPrevia;

    @OneToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;
}
