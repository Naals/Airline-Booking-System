package com.project.commonlib.payload.response;

import com.project.commonlib.embeddable.Address;
import com.project.commonlib.embeddable.GeoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponse {

    private Long id;
    private String iataCode;
    private String name;
    private CityResponse city;
    private Address address;
    private GeoCode geoCode;
    private ZoneId timeZoneId;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
