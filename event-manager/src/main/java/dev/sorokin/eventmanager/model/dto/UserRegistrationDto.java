package dev.sorokin.eventmanager.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(
        @NotBlank(message = "Логин не может быть пустым")
        @Size(min = 3, max = 50, message = "Пароль должен содержать от 8 до 100 символов")
        String login,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 8, max = 100, message = "Логин должен содержать от 3 до 50 символов")
        String password,

        @NotNull(message = "Возраст обязателен")
        @Min(value = 18, message = "Возраст должен быть не меньше 18 лет")
        Integer age
) {
}
