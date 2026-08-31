package dev.sorokin.eventmanager.controller;

import dev.sorokin.eventmanager.model.dto.LocationDto;
import dev.sorokin.eventmanager.service.LocationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/locations")
@Validated
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "EventManager starter is running");
    }

    @PostMapping
    public ResponseEntity<LocationDto> create(@Valid @RequestBody LocationDto request) {
        LocationDto createdLocation = locationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAll() {
        return ResponseEntity.ok(locationService.getAll());
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDto> getById(@PathVariable("locationId") @Min(1) Long locationId) {
        LocationDto location = locationService.getById(locationId);
        return ResponseEntity.ok(location);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<LocationDto> update(
            @PathVariable("locationId") @Min(1) Long locationToUpdateId,
            @Valid @RequestBody LocationDto request) {
        LocationDto updatedLocation = locationService.update(locationToUpdateId, request);
        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> delete(@PathVariable("locationId") @Min(1) Long locationId) {
        locationService.delete(locationId);
        return ResponseEntity.noContent().build();
    }
}