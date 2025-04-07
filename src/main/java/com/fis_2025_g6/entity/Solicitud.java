package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

import com.fis_2025_g6.EstadoSolicitud;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    @ManyToOne
    @JoinColumn(name = "adoptante_id", nullable = false)
    private Adoptante adoptante;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;
}
