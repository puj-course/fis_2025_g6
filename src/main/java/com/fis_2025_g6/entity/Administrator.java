package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "administrador")
@Data
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue("ADMIN")
public class Administrator extends User {}
