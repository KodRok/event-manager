package dev.sorokin.eventmanager.service;

import dev.sorokin.eventmanager.exception.AuthenticationFailedException;
import dev.sorokin.eventmanager.exception.UserAlreadyExistsException;
import dev.sorokin.eventmanager.exception.UserNotFoundException;
import dev.sorokin.eventmanager.mapper.UserMapper;
import dev.sorokin.eventmanager.model.domain.user.Role;
import dev.sorokin.eventmanager.model.domain.user.User;
import dev.sorokin.eventmanager.model.dto.UserCredentialsDto;
import dev.sorokin.eventmanager.model.dto.UserRegistrationDto;
import dev.sorokin.eventmanager.model.entity.UserEntity;
import dev.sorokin.eventmanager.repository.UserRepository;
import dev.sorokin.eventmanager.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public User registerUser(UserRegistrationDto dto) {
        if (userRepository.existsByLogin(dto.login())) {
            throw new UserAlreadyExistsException(
                    "Пользователь с логином '" + dto.login() + "' уже существует");
        }

        String hashedPassword = passwordEncoder.encode(dto.password());

        User newUser = userMapper.toDomain(dto, hashedPassword, Role.USER);

        UserEntity entity = userMapper.toEntity(newUser);

        UserEntity saved = userRepository.save(entity);
        return userMapper.toDomain(saved);
    }

    @Transactional(readOnly = true)
    public String authenticate(UserCredentialsDto dto) {
        UserEntity entity = userRepository.findByLogin(dto.login())
                .orElseThrow(() -> new AuthenticationFailedException("Неверный логин или пароль"));

        if (!passwordEncoder.matches(dto.password(), entity.getPasswordHash())) {
            throw new AuthenticationFailedException("Неверный логин или пароль");
        }

        User user = userMapper.toDomain(entity);
        return jwtService.generateToken(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDomain)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id=" + userId + " не найден"));
    }
}
