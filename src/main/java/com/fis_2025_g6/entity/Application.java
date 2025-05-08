package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

import com.fis_2025_g6.ApplicationState;

@Entity
@Table(name = "solicitud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solic_id")
    private Long id;

    @Column(name = "solic_fecha", nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "solic_estado", nullable = false)
    private ApplicationState state;

    @ManyToOne
    @JoinColumn(name = "adopt_id", nullable = false)
    private Adoptant adoptant;

    @ManyToOne
    @JoinColumn(name = "masc_id", nullable = false)
    private Pet pet;
}
