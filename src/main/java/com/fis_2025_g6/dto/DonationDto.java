package com.fis_2025_g6.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
public class DonationDto {
    @NonNull
    @Min(1)
    private Double amount;

    @NotNull
    private String refugeUsername;
}
