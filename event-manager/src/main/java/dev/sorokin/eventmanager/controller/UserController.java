package dev.sorokin.eventmanager.controller;

import dev.sorokin.eventmanager.mapper.UserMapper;
import dev.sorokin.eventmanager.model.domain.user.User;
import dev.sorokin.eventmanager.model.dto.UserCredentialsDto;
import dev.sorokin.eventmanager.model.dto.UserDto;
import dev.sorokin.eventmanager.model.dto.UserRegistrationDto;
import dev.sorokin.eventmanager.model.dto.responses.JwtResponse;
import dev.sorokin.eventmanager.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRegistrationDto registrationDto) {
        log.info("Запрос на регистрацию пользователя с логином: {}", registrationDto.login());
        User createdUser = userService.registerUser(registrationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toDto(createdUser));
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody @Valid UserCredentialsDto credentialsDto) {
        log.info("Запрос на аутентификацию пользователя: {}", credentialsDto.login());
        String token = userService.authenticate(credentialsDto);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable @Positive(message = "ID пользователя должен быть положительным числом") Long userId
    ) {
        log.info("Запрос на получение пользователя по id={}", userId);
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
