package com.fis_2025_g6.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "adoptante")
@Data
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue("ADOPTANTE")
@NoArgsConstructor
@AllArgsConstructor
public class Adoptant extends User {
    @Column(name = "adopt_nombreAdopt", nullable = false)
    private String adoptantName;

    @OneToMany(mappedBy = "adoptant")
    @JsonIgnore
    private Set<Donation> donations;

    @OneToMany(mappedBy = "adoptant")
    @JsonIgnore
    private Set<Application> applications;
}
