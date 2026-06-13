package com.project.locationservice.controller;

import com.project.commonlib.payload.request.CityRequest;
import com.project.commonlib.payload.response.ApiResponse;
import com.project.commonlib.payload.response.CityResponse;
import com.project.locationservice.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
@Slf4j
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<ApiResponse<CityResponse>> createCity(@Valid @RequestBody CityRequest cityRequest) {
        log.info("REST request to save City : {}", cityRequest.getCityCode());
        CityResponse response = cityService.createCity(cityRequest);
        return new ResponseEntity<>(
                ApiResponse.success(response, "City created successfully"),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CityResponse>> getCityById(@PathVariable Long id) {
        log.info("REST request to get City by ID : {}", id);
        CityResponse response = cityService.getCityById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "City retrieved successfully"));
    }

    @GetMapping("/code/{cityCode}")
    public ResponseEntity<ApiResponse<CityResponse>> getCityByCode(@PathVariable String cityCode) {
        log.info("REST request to get City by Code : {}", cityCode);
        CityResponse response = cityService.getCityByCode(cityCode.toUpperCase());
        return ResponseEntity.ok(ApiResponse.success(response, "City retrieved successfully"));
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<ApiResponse<Page<CityResponse>>> getCitiesByCountryCode(
            @PathVariable String countryCode,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        log.info("REST request to get paginated cities for country code : {}", countryCode);
        Page<CityResponse> response = cityService.getCitiesByCountryCode(countryCode, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Country cities retrieved successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<CityResponse>>> searchCities(
            @RequestParam String keyword,
            @PageableDefault Pageable pageable) {
        log.info("REST request to search cities with keyword : {}", keyword);
        Page<CityResponse> response = cityService.searchCities(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Search results retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CityResponse>>> getAllCities(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        log.info("REST request to get a paginated list of all cities");
        Page<CityResponse> response = cityService.getAllCities(pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "All cities retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CityResponse>> updateCity(
            @PathVariable Long id,
            @Valid @RequestBody CityRequest cityRequest) {
        log.info("REST request to update City ID : {}", id);
        CityResponse response = cityService.updateCity(id, cityRequest);
        return ResponseEntity.ok(ApiResponse.success(response, "City updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCity(@PathVariable Long id) {
        log.info("REST request to delete City ID : {}", id);
        cityService.deleteCity(id);
        return ResponseEntity.ok(ApiResponse.success(null, "City deleted successfully"));
    }

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<ApiResponse<Boolean>> checkCityExistence(@PathVariable String cityCode) {
        log.info("REST request to check if City Code exists : {}", cityCode);
        boolean exists = cityService.existsCityByCode(cityCode);
        return ResponseEntity.ok(ApiResponse.success(exists, "City existence check completed"));
    }
}