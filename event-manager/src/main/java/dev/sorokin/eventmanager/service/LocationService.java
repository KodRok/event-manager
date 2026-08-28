package dev.sorokin.eventmanager.service;

import dev.sorokin.eventmanager.exception.LocationNotFoundException;
import dev.sorokin.eventmanager.mapper.LocationMapper;
import dev.sorokin.eventmanager.model.domain.Location;
import dev.sorokin.eventmanager.model.dto.LocationDto;
import dev.sorokin.eventmanager.model.entity.LocationEntity;
import dev.sorokin.eventmanager.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepository repository;
    private final LocationMapper mapper;

    @Transactional
    public LocationDto create(LocationDto dto) {
        LocationEntity entity = mapper.toEntity(mapper.toDomain(dto));
        LocationEntity savedEntity = repository.save(entity);
        return mapper.toDto(mapper.toDomain(savedEntity));
    }

    public LocationDto getById(Long id) {
        LocationEntity entity = repository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location with id " + id + " not found"));
        return mapper.toDto(mapper.toDomain(entity));
    }

    public List<LocationDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public LocationDto update(Long id, LocationDto newLocationDto) {
        if (!repository.existsById(id)) {
            throw new LocationNotFoundException("Location with id " + id + " not found");
        }

        LocationEntity entityToUpdate = mapper.toEntity(mapper.toDomain(newLocationDto));
        entityToUpdate.setId(id);

        LocationEntity savedEntity = repository.save(entityToUpdate);
        return mapper.toDto(mapper.toDomain(savedEntity));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new LocationNotFoundException("Location with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}