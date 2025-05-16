package com.fis_2025_g6.dto;

import com.fis_2025_g6.ApplicationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateApplicationStatusDto {
    private ApplicationStatus status;
}
