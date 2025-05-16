package com.fis_2025_g6.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FormDto {
    @NotNull
    private Long applicationId;

    @NotNull
    private boolean hasPets;

    @NotNull
    private boolean hasRoom;

    @NotBlank
    private String housingType;

    @Min(0)
    private Integer hoursAwayFromHome;

    @NotNull
    private boolean vaccinationCommitment;

    @NotBlank
    private String previousExperience;
}
