package com.project.commonlib.payload.request; // Adjust to match your request module package

import com.project.commonlib.embeddable.Support;
import com.project.commonlib.enums.AirlineStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineRequest {

    @NotBlank(message = "IATA code is required")
    @Size(min = 2, max = 3, message = "IATA code must be between 2 and 3 characters")
    private String iataCode;

    @NotBlank(message = "ICAO code is required")
    @Size(min = 3, max = 4, message = "ICAO code must be between 3 and 4 characters")
    private String icaoCode;

    @NotBlank(message = "Airline name is required")
    @Size(max = 100, message = "Airline name cannot exceed 100 characters")
    private String name;

    private String alias;

    private String logoUrl;

    private String website;

    private String alliance;

    private AirlineStatus status;

    @NotNull(message = "Headquarters city ID is required")
    private Long headquartersCityId;

    private Long ownerId;

    @Valid
    private Support support;
}