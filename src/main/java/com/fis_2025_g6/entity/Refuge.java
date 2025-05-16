package com.fis_2025_g6.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refugio")
@Data
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue("REFUGIO")
@NoArgsConstructor
@AllArgsConstructor
public class Refuge extends User {
    @Column(name = "refg_nombreRefg", nullable = false)
    private String refugeName;

    @Column(name = "refg_descripcion", nullable = false)
    private String description;

    @OneToMany(mappedBy = "refuge")
    @JsonIgnore
    private Set<Pet> pets;

    @OneToMany(mappedBy = "refuge")
    @JsonIgnore
    private Set<Donation> receivedDonations;
}
