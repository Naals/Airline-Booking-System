package com.project.commonlib.payload.response; // Adjust to match your package path

import com.project.commonlib.embeddable.Support;
import com.project.commonlib.enums.AirlineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponse {

    private String name;
    private String alias;

    private String logoUrl;
    private String website;

    private AirlineStatus status;
    private String alliance;

    private Instant createdAt;
    private Instant updatedAt;

    private Long ownerId;
    private UserResponse owner;
    private Long updatedById;

    private CityResponse headquartersCity;
    private Support support;
}