package com.project.locationservice.controller;

import com.project.commonlib.payload.request.AirportRequest;
import com.project.commonlib.payload.response.ApiResponse;
import com.project.commonlib.payload.response.AirportResponse;
import com.project.locationservice.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airports")
@RequiredArgsConstructor
@Slf4j
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<ApiResponse<AirportResponse>> createAirport(@Valid @RequestBody AirportRequest airportRequest) {
        log.info("REST request to build a new Airport hub: {}", airportRequest.getIataCode());
        AirportResponse response = airportService.createAirport(airportRequest);
        return new ResponseEntity<>(
                ApiResponse.success(response, "Airport registered successfully"),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirportResponse>> getAirportById(@PathVariable Long id) {
        log.info("REST request to fetch Airport by ID: {}", id);
        AirportResponse response = airportService.getAirportById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Airport retrieved successfully"));
    }

    @GetMapping("/code/{iataCode}")
    public ResponseEntity<ApiResponse<AirportResponse>> getAirportByIataCode(@PathVariable String iataCode) {
        log.info("REST request to fetch Airport by IATA code: {}", iataCode);
        AirportResponse response = airportService.getAirportByIataCode(iataCode);
        return ResponseEntity.ok(ApiResponse.success(response, "Airport retrieved successfully"));
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<ApiResponse<List<AirportResponse>>> getAirportsByCityId(@PathVariable Long cityId) {
        log.info("REST request to fetch all airports matching city ID: {}", cityId);
        List<AirportResponse> response = airportService.getAirportsByCityId(cityId);
        return ResponseEntity.ok(ApiResponse.success(response, "Airports for designated city retrieved"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AirportResponse>>> getAllAirports(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        log.info("REST request to fetch a paginated grid of all airports");
        Page<AirportResponse> response = airportService.getAllAirports(pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "All airports retrieved successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<AirportResponse>>> searchAirports(
            @RequestParam String keyword,
            @PageableDefault(size = 15) Pageable pageable) {
        log.info("REST request to filter airports using keyword: {}", keyword);
        Page<AirportResponse> response = airportService.searchAirports(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Search execution completed"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AirportResponse>> updateAirport(
            @PathVariable Long id,
            @Valid @RequestBody AirportRequest airportRequest) {
        log.info("REST request to update existing Airport metadata matching ID: {}", id);
        AirportResponse response = airportService.updateAirport(id, airportRequest);
        return ResponseEntity.ok(ApiResponse.success(response, "Airport structural settings modified successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAirport(@PathVariable Long id) {
        log.info("REST request to wipe Airport matching ID from configurations: {}", id);
        airportService.deleteAirport(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Airport successfully removed from system"));
    }
}