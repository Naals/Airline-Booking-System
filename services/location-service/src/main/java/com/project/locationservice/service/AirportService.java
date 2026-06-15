package com.project.locationservice.service;

import com.project.commonlib.payload.request.AirportRequest;
import com.project.commonlib.payload.response.AirportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest airportRequest);
    AirportResponse getAirportById(Long id);
    AirportResponse getAirportByIataCode(String iataCode);
    List<AirportResponse> getAirportsByCityId(Long cityId);
    Page<AirportResponse> getAllAirports(Pageable pageable);
    Page<AirportResponse> searchAirports(String keyword, Pageable pageable);
    AirportResponse updateAirport(Long id, AirportRequest airportRequest);
    void deleteAirport(Long id);
}
