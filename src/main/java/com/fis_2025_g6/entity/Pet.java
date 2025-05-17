package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fis_2025_g6.AdoptionStatus;

@Entity
@Table(name = "mascota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "masc_id")
    private Long id;

    @Column(name = "masc_nombre", nullable = false)
    private String name;

    @Column(name = "masc_especie", nullable = false)
    private String species;

    @Column(name = "masc_edad", nullable = false)
    private Integer age;

    @Column(name = "masc_sexo", nullable = false)
    private String sex;

    @Column(name = "masc_descripcion", nullable = false)
    private String description;

    @Column(name = "masc_fechaRegistro", nullable = false)
    private Date registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "masc_estado", nullable = false)
    private AdoptionStatus status = AdoptionStatus.AVAILABLE;

    @Column(name = "masc_fotoUrl")
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "refg_id", nullable = false)
    private Refuge refuge;

    @OneToMany(mappedBy = "pet")
    @JsonIgnore
    private Set<Application> applications;

    public String getFotoUrl() {
        return this.photoUrl;
    }

    public String getNombre() {
        return this.name;
    }
}
