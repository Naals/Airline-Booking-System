package com.project.airlinecoreservice.controller;

import com.project.commonlib.enums.AirlineStatus;
import com.project.commonlib.payload.request.AirlineRequest;
import com.project.commonlib.payload.response.AirlineDropdownItem;
import com.project.commonlib.payload.response.AirlineResponse;
import com.project.commonlib.payload.response.ApiResponse;
import com.project.airlinecoreservice.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airlines")
@RequiredArgsConstructor
@Slf4j
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<ApiResponse<AirlineResponse>> createAirline(
            @Valid @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long userId) {
        log.info("Received request from user {} to create airline: {}", userId, request.getName());
        AirlineResponse response = airlineService.createAirline(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Airline created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirlineResponse>> getAirlineById(@PathVariable Long id) {
        log.info("Received request to fetch airline by ID: {}", id);
        AirlineResponse response = airlineService.getAirlineById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/iata/{iataCode}")
    public ResponseEntity<ApiResponse<AirlineResponse>> getAirlineByIataCode(@PathVariable String iataCode) {
        log.info("Received request to fetch airline by IATA code: {}", iataCode);
        AirlineResponse response = airlineService.getAirlineByIataCode(iataCode);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/icao/{icaoCode}")
    public ResponseEntity<ApiResponse<AirlineResponse>> getAirlineByIcaoCode(@PathVariable String icaoCode) {
        log.info("Received request to fetch airline by ICAO code: {}", icaoCode);
        AirlineResponse response = airlineService.getAirlineByIcaoCode(icaoCode);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirlineResponse>>> getAllAirlines() {
        log.info("Received request to fetch all airlines");
        List<AirlineResponse> response = airlineService.getAllAirlines();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AirlineResponse>>> getAirlinesByStatus(@PathVariable AirlineStatus status) {
        log.info("Received request to fetch airlines with status: {}", status);
        List<AirlineResponse> response = airlineService.getAirlinesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/dropdown")
    public ResponseEntity<ApiResponse<List<AirlineDropdownItem>>> getAirlinesDropdown() {
        log.info("Received request to fetch lightweight airline dropdown list");
        List<AirlineDropdownItem> response = airlineService.getAirlinesDropdown();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AirlineResponse>> updateAirline(
            @PathVariable Long id,
            @Valid @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long userId) {
        log.info("Received request from user {} to update airline ID: {}", userId, id);
        AirlineResponse response = airlineService.updateAirline(id, request, userId);
        return ResponseEntity.ok(ApiResponse.success(response, "Airline updated successfully"));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateAirlineStatus(
            @PathVariable Long id,
            @RequestParam AirlineStatus status,
            @RequestHeader("X-User-Id") Long userId) {
        log.info("Received status change request from user {} for airline ID: {} to {}", userId, id, status);
        airlineService.updateAirlineStatus(id, status, userId);
        return ResponseEntity.ok(ApiResponse.success(null, "Airline status modified to " + status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAirline(@PathVariable Long id) {
        log.info("Received administrative request to delete airline ID: {}", id);
        airlineService.deleteAirline(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Airline record deleted permanently"));
    }
}