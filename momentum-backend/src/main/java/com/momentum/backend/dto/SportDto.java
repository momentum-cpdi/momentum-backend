package com.momentum.backend.dto;

import com.momentum.backend.entity.SportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SportDto(
    Long id,

    @NotBlank(message = "Le nom du sport est obligatoire.")
    String name,

    @NotBlank(message = "Le code du sport est obligatoire.")
    String code,

    @NotNull(message = "Le type du sport est obligatoire.")
    SportType type,

    boolean active
) {
}
