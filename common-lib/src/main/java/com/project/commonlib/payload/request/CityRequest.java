package com.project.commonlib.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityRequest {

    @NotBlank(message = "City code is required")
    @Size(min = 3, max = 3, message = "City code must be exactly 3 characters")
    private String cityCode;

    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 3, message = "Country code must be 2 or 3 characters")
    private String countryCode;

    @NotBlank(message = "City name is required")
    @Size(max = 100, message = "City name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Country name is required")
    @Size(max = 100, message = "Country name cannot exceed 100 characters")
    private String country;

    @NotBlank(message = "Time zone is required")
    @Size(max = 50, message = "Time zone cannot exceed 50 characters")
    private String timeZone;

    @Size(max = 10, message = "Region code cannot exceed 10 characters")
    private String regionCode;
}