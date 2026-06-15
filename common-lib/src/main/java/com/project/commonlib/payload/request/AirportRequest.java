package com.project.commonlib.payload.request;

import com.project.commonlib.embeddable.Address;
import com.project.commonlib.embeddable.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequest {

    @NotBlank(message = "Airport IATA code is required")
    @Size(min = 3, max = 3, message = "IATA code must be exactly 3 characters")
    private String iataCode;

    @NotBlank(message = "Airport name is required")
    @Size(max = 150, message = "Airport name cannot exceed 150 characters")
    private String name;

    @NotNull(message = "Associated City ID is required")
    private Long cityId;

    @Valid
    @NotNull(message = "Address details are required")
    private Address address;

    @Valid
    @NotNull(message = "Geographic coordinates are required")
    private GeoCode geoCode;

    @NotBlank(message = "Time zone ID is required")
    @Size(max = 50, message = "Time zone ID cannot exceed 50 characters")
    private String timeZoneId;

    @Builder.Default
    private boolean isActive = true;
}
