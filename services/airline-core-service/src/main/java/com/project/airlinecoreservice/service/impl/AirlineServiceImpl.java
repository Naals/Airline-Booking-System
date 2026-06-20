package com.project.airlinecoreservice.service.impl;

import com.project.airlinecoreservice.mapper.AirlineMapper;
import com.project.commonlib.enums.AirlineStatus;
import com.project.commonlib.exception.DuplicateResourceException;
import com.project.commonlib.exception.ResourceNotFoundException;
import com.project.commonlib.payload.request.AirlineRequest;
import com.project.commonlib.payload.response.AirlineDropdownItem;
import com.project.commonlib.payload.response.AirlineResponse;
import com.project.airlinecoreservice.modal.Airline;
import com.project.airlinecoreservice.repository.AirlineRepository;
import com.project.airlinecoreservice.service.AirlineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    @Transactional
    public AirlineResponse createAirline(AirlineRequest request, Long userId) {
        log.info("Processing creation request for airline: {} by user: {}", request.getName(), userId);

        if (airlineRepository.existsByIataCode(request.getIataCode())) {
            throw new DuplicateResourceException("Airline with IATA code " + request.getIataCode() + " already exists.");
        }
        if (airlineRepository.existsByIcaoCode(request.getIcaoCode())) {
            throw new DuplicateResourceException("Airline with ICAO code " + request.getIcaoCode() + " already exists.");
        }

        Airline airline = Airline.builder()
                .iataCode(request.getIataCode().toUpperCase())
                .icaoCode(request.getIcaoCode().toUpperCase())
                .name(request.getName())
                .alias(request.getAlias())
                .logoUrl(request.getLogoUrl())
                .website(request.getWebsite())
                .alliance(request.getAlliance())
                .status(request.getStatus() != null ? request.getStatus() : AirlineStatus.ACTIVE)
                .headquartersCityId(request.getHeadquartersCityId())
                .ownerId(request.getOwnerId())
                .updatedById(userId)
                .support(request.getSupport())
                .build();

        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineResponse getAirlineById(Long id) {
        log.info("Fetching airline details for ID: {}", id);
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with ID: " + id));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineResponse getAirlineByIataCode(String iataCode) {
        log.info("Fetching airline details for IATA code: {}", iataCode);
        Airline airline = airlineRepository.findByIataCode(iataCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with IATA code: " + iataCode));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineResponse getAirlineByIcaoCode(String icaoCode) {
        log.info("Fetching airline details for ICAO code: {}", icaoCode);
        Airline airline = airlineRepository.findByIcaoCode(icaoCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with ICAO code: " + icaoCode));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirlineResponse> getAllAirlines() {
        log.info("Fetching all airlines across the system");
        return airlineRepository.findAll().stream()
                .map(AirlineMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirlineResponse> getAirlinesByStatus(AirlineStatus status) {
        log.info("Fetching airlines filtered by status: {}", status);
        return airlineRepository.findByStatus(status).stream()
                .map(AirlineMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AirlineResponse updateAirline(Long id, AirlineRequest request, Long userId) {
        log.info("Updating profile details for airline ID: {} by user: {}", id, userId);
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with ID: " + id));

        if (!airline.getIataCode().equalsIgnoreCase(request.getIataCode()) && airlineRepository.existsByIataCode(request.getIataCode())) {
            throw new DuplicateResourceException("Airline with IATA code " + request.getIataCode() + " already exists.");
        }
        if (!airline.getIcaoCode().equalsIgnoreCase(request.getIcaoCode()) && airlineRepository.existsByIcaoCode(request.getIcaoCode())) {
            throw new DuplicateResourceException("Airline with ICAO code " + request.getIcaoCode() + " already exists.");
        }

        airline.setIataCode(request.getIataCode().toUpperCase());
        airline.setIcaoCode(request.getIcaoCode().toUpperCase());
        airline.setName(request.getName());
        airline.setAlias(request.getAlias());
        airline.setLogoUrl(request.getLogoUrl());
        airline.setWebsite(request.getWebsite());
        airline.setAlliance(request.getAlliance());
        if (request.getStatus() != null) {
            airline.setStatus(request.getStatus());
        }
        airline.setHeadquartersCityId(request.getHeadquartersCityId());
        airline.setOwnerId(request.getOwnerId());
        airline.setUpdatedById(userId);
        airline.setSupport(request.getSupport());

        Airline updatedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(updatedAirline);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirlineDropdownItem> getAirlinesDropdown() {
        log.info("Fetching lightweight metadata for all dropdown items");
        return airlineRepository.findAll().stream()
                .map(airline -> AirlineDropdownItem.builder()
                        .id(airline.getId())
                        .name(airline.getName())
                        .iataCode(airline.getIataCode())
                        .icaoCode(airline.getIcaoCode())
                        .logoUrl(airline.getLogoUrl())
                        .country(null)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateAirlineStatus(Long id, AirlineStatus status, Long userId) {
        log.info("Administratively changing status of airline ID: {} to {} by user: {}", id, status, userId);
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with ID: " + id));

        airline.setStatus(status);
        airline.setUpdatedById(userId);
        airlineRepository.save(airline);
    }

    @Override
    @Transactional
    public void deleteAirline(Long id) {
        log.info("Deleting airline record with ID: {}", id);
        if (!airlineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Airline not found with ID: " + id);
        }
        airlineRepository.deleteById(id);
    }

}