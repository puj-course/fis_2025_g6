package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "donacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donac_id")
    private Long id;

    @Column(name = "donac_monto", nullable = false)
    private Double amount;

    @Column(name = "donac_fecha", nullable = false)
    private Date date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "adopt_id")
    private Adoptant adoptant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "refg_id")
    private Refuge refuge;
}
