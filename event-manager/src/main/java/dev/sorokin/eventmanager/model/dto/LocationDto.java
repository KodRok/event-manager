package dev.sorokin.eventmanager.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocationDto(
        Long id,

        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Address cannot be empty")
        String address,

        @NotNull(message = "Capacity cannot be null")
        @Min(value = 5, message = "Capacity must be greater than or equal to 5")
        Integer capacity,

        String description
) {}