package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private Date fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "adoptante_id")
    private Adoptante adoptante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "refugio_id")
    private Refugio refugio;
}
