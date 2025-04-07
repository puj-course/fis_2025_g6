package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

import com.fis_2025_g6.EstadoAdopcion;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false)
    private String sexo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Date fechaRegistro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAdopcion estadoAdopcion = EstadoAdopcion.DISPONIBLE;

    @ManyToOne
    @JoinColumn(name = "refugio_id", nullable = false)
    private Refugio refugio;

    @OneToMany(mappedBy = "mascota")
    private Set<Solicitud> solicitudes;
}
