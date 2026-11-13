package dev.sorokin.eventmanager.controller;

import dev.sorokin.eventmanager.model.dto.LocationDto;
import dev.sorokin.eventmanager.service.LocationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(LocationController.class);

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "EventManager starter is running");
    }

    @PostMapping
    public ResponseEntity<LocationDto> create(@Valid @RequestBody LocationDto request) {
        log.info("Get request for location create: locationDto={}", request);
        LocationDto createdLocation = locationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAll() {
        log.info("Get request for get All locations");
        return ResponseEntity.ok(locationService.getAll());
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> delete(@PathVariable("locationId") @Min(1) Long locationId) {
        log.info("Get request for delete location: locationDto={}", locationId);
        locationService.delete(locationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDto> getById(@PathVariable("locationId") @Min(1) Long locationId) {
        log.info("Get request for get location by Id: locationDto={}", locationId);
        LocationDto location = locationService.getById(locationId);
        return ResponseEntity.ok(location);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<LocationDto> update(
            @PathVariable("locationId") @Min(1) Long locationToUpdateId,
            @Valid @RequestBody LocationDto request) {
        log.info("Get request for update location: locationToUpdateId={}, locationDto={}",
                locationToUpdateId, request);
        LocationDto updatedLocation = locationService.update(locationToUpdateId, request);
        return ResponseEntity.ok(updatedLocation);
    }
}