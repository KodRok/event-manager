package dev.sorokin.eventmanager.model.domain.user;

public record User(
        Long id,
        String login,
        String passwordHash,
        Integer age,
        Role role
) {
}
