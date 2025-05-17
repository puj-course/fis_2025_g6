package com.fis_2025_g6.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PetDto {
    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    private String species;

    @Min(0)
    private Integer age;

    @NotBlank
    private String sex;

    @NotBlank
    private String description;

    @Size(max = 255)
    private String photoUrl;
}
