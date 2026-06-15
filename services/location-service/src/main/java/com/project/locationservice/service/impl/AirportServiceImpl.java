package com.project.locationservice.service.impl;

import com.project.commonlib.exception.DuplicateResourceException;
import com.project.commonlib.exception.ResourceNotFoundException;
import com.project.commonlib.payload.request.AirportRequest;
import com.project.commonlib.payload.response.AirportResponse;
import com.project.locationservice.mapper.AirportMapper;
import com.project.locationservice.modal.Airport;
import com.project.locationservice.modal.City;
import com.project.locationservice.repository.AirportRepository;
import com.project.locationservice.repository.CityRepository;
import com.project.locationservice.service.AirportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    private final AirportMapper airportMapper;

    @Override
    @Transactional
    public AirportResponse createAirport(AirportRequest airportRequest) {
        log.info("Creating airport with IATA code: {}", airportRequest.getIataCode());

        if (airportRepository.existsByIataCode(airportRequest.getIataCode())) {
            throw new DuplicateResourceException("Airport with IATA code " + airportRequest.getIataCode() + " already exists.");
        }

        City city = cityRepository.findById(airportRequest.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot create airport. City not found with ID: " + airportRequest.getCityId()));

        Airport airport = airportMapper.toEntity(airportRequest);
        airport.setCity(city);

        Airport savedAirport = airportRepository.save(airport);
        return airportMapper.toResponse(savedAirport);
    }

    @Override
    @Transactional(readOnly = true)
    public AirportResponse getAirportById(Long id) {
        log.info("Fetching airport with ID: {}", id);
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with ID: " + id));
        return airportMapper.toResponse(airport);
    }

    @Override
    @Transactional(readOnly = true)
    public AirportResponse getAirportByIataCode(String iataCode) {
        log.info("Fetching airport with IATA code: {}", iataCode);
        Airport airport = airportRepository.findByIataCode(iataCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with IATA code: " + iataCode));
        return airportMapper.toResponse(airport);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirportResponse> getAirportsByCityId(Long cityId) {
        log.info("Fetching airports associated with city ID: {}", cityId);

        if (!cityRepository.existsById(cityId)) {
            throw new ResourceNotFoundException("City not found with ID: " + cityId);
        }

        return airportRepository.findByCityId(cityId).stream()
                .map(airportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AirportResponse> getAllAirports(Pageable pageable) {
        log.info("Fetching paginated list of all airports");
        return airportRepository.findAll(pageable)
                .map(airportMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AirportResponse> searchAirports(String keyword, Pageable pageable) {
        log.info("Searching airports with keyword: {}", keyword);
        return airportRepository.searchAirports(keyword, pageable)
                .map(airportMapper::toResponse);
    }

    @Override
    @Transactional
    public AirportResponse updateAirport(Long id, AirportRequest airportRequest) {
        log.info("Updating airport with ID: {}", id);

        Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with ID: " + id));

        if (!existingAirport.getIataCode().equalsIgnoreCase(airportRequest.getIataCode())
                && airportRepository.existsByIataCode(airportRequest.getIataCode())) {
            throw new DuplicateResourceException("IATA code " + airportRequest.getIataCode() + " is already assigned to another hub.");
        }

        if (!existingAirport.getCity().getId().equals(airportRequest.getCityId())) {
            City newCity = cityRepository.findById(airportRequest.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException("City not found with ID: " + airportRequest.getCityId()));
            existingAirport.setCity(newCity);
        }

        airportMapper.updateEntityFromRequest(airportRequest, existingAirport);

        Airport updatedAirport = airportRepository.save(existingAirport);
        return airportMapper.toResponse(updatedAirport);
    }

    @Override
    @Transactional
    public void deleteAirport(Long id) {
        log.info("Deleting airport with ID: {}", id);
        if (!airportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airport not found with ID: " + id);
        }
        airportRepository.deleteById(id);
    }
}