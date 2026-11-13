package dev.sorokin.eventmanager.security;

import dev.sorokin.eventmanager.model.domain.user.Role;
import dev.sorokin.eventmanager.model.entity.UserEntity;
import dev.sorokin.eventmanager.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        String adminLogin = "admin";
        if (!userRepository.existsByLogin(adminLogin)) {
            UserEntity admin = new UserEntity();
            admin.setLogin(adminLogin);
            admin.setPasswordHash(passwordEncoder.encode("admin"));
            admin.setAge(30);
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }
}