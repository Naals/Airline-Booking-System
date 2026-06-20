package com.project.airlinecoreservice.service;

import com.project.commonlib.payload.request.AirlineRequest;
import com.project.commonlib.payload.response.AirlineResponse;
import com.project.commonlib.enums.AirlineStatus;

import java.util.List;

public interface AirlineService {

    AirlineResponse createAirline(AirlineRequest request, Long userId);
    AirlineResponse getAirlineById(Long id);
    AirlineResponse getAirlineByIataCode(String iataCode);
    AirlineResponse getAirlineByIcaoCode(String icaoCode);
    List<AirlineResponse> getAllAirlines();
    List<AirlineResponse> getAirlinesByStatus(AirlineStatus status);
    AirlineResponse updateAirline(Long id, AirlineRequest request, Long userId);
    void updateAirlineStatus(Long id, AirlineStatus status, Long userId);
    void deleteAirline(Long id);
}