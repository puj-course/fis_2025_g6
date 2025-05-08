package com.fis_2025_g6.entity;

import org.springframework.boot.context.properties.bind.Name;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "formulario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("form_id")
    private Long id;

    @Column(name = "form_tieneMascotas", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean hasPets;

    @Column(name = "form_tieneEspacio", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean hasRoom;

    @Column(name = "form_tipoVivienda", nullable = false)
    private String housingType;

    @Column(name = "form_horasFuera", nullable = false)
    private Integer hoursAwayFromHome;

    @Column(name = "form_comprVacuna", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean vaccinationCommitment;

    @Column(name = "form_expPrevia", nullable = false)
    private String previousExperience;

    @OneToOne
    @JoinColumn(name = "solic_id", nullable = false)
    private Application application;
}
