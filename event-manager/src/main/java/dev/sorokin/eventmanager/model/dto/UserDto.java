package dev.sorokin.eventmanager.model.dto;

import dev.sorokin.eventmanager.model.domain.user.Role;

public record UserDto(
        Long id,
        String login,
        Integer age,
        Role role
) {
}
