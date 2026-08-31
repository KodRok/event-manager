package dev.sorokin.eventmanager.mapper;

import dev.sorokin.eventmanager.model.domain.Location;
import dev.sorokin.eventmanager.model.dto.LocationDto;
import dev.sorokin.eventmanager.model.entity.LocationEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location toDomain(LocationDto dto) {
        if (dto == null) {
            return null;
        }
        return new Location(
                dto.id(),
                dto.name(),
                dto.address(),
                dto.capacity(),
                dto.description()
        );
    }

    public LocationDto toDto(Location domain) {
        if (domain == null) {
            return null;
        }
        return new LocationDto(
                domain.id(),
                domain.name(),
                domain.address(),
                domain.capacity(),
                domain.description()
        );
    }

    public LocationEntity toEntity(Location domain) {
        if (domain == null) {
            return null;
        }
        return new LocationEntity(
                domain.id(),
                domain.name(),
                domain.address(),
                domain.capacity(),
                domain.description()
        );
    }

    public Location toDomain(LocationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Location(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getCapacity(),
                entity.getDescription()
        );
    }
}
