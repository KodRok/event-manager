package dev.sorokin.eventmanager.mapper;

import dev.sorokin.eventmanager.model.domain.user.Role;
import dev.sorokin.eventmanager.model.domain.user.User;
import dev.sorokin.eventmanager.model.dto.UserDto;
import dev.sorokin.eventmanager.model.dto.UserRegistrationDto;
import dev.sorokin.eventmanager.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserRegistrationDto dto, String passwordHash, Role role) {
        if (dto == null) {
            return null;
        }
        return new User(
                null,
                dto.login(),
                passwordHash,
                dto.age(),
                role
        );
    }

    public UserDto toDto(User domain) {
        if (domain == null) {
            return null;
        }
        return new UserDto(
                domain.id(),
                domain.login(),
                domain.age(),
                domain.role()
        );
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        return new UserEntity(
                domain.id(),
                domain.login(),
                domain.passwordHash(),
                domain.age(),
                domain.role()
        );
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
                entity.getId(),
                entity.getLogin(),
                entity.getPasswordHash(),
                entity.getAge(),
                entity.getRole()
        );
    }
}
