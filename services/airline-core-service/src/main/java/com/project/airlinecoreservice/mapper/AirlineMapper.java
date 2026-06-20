package com.project.airlinecoreservice.mapper;

import com.project.airlinecoreservice.modal.Airline;
import com.project.commonlib.payload.response.AirlineDropdownItem;
import com.project.commonlib.payload.response.AirlineResponse;
import org.springframework.stereotype.Component;

@Component
public class AirlineMapper {


    public static AirlineResponse toResponse(Airline airline) {
        if (airline == null) {
            return null;
        }

        return AirlineResponse.builder()
                .name(airline.getName())
                .alias(airline.getAlias())
                .logoUrl(airline.getLogoUrl())
                .website(airline.getWebsite())
                .status(airline.getStatus())
                .alliance(airline.getAlliance())
                .createdAt(airline.getCreatedAt())
                .updatedAt(airline.getUpdatedAt())
                .ownerId(airline.getOwnerId())
                .updatedById(airline.getUpdatedById())
                .support(airline.getSupport())
                .headquartersCityId(airline.getHeadquartersCityId())
                .build();
    }


    public static AirlineDropdownItem toDropdownItem(Airline airline, String countryName) {
        if (airline == null) {
            return null;
        }

        return AirlineDropdownItem.builder()
                .id(airline.getId())
                .name(airline.getName())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .logoUrl(airline.getLogoUrl())
                .country(countryName)
                .build();
    }
}