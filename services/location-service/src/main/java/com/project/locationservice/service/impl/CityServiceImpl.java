package com.project.locationservice.service.impl;

import com.project.commonlib.exception.DuplicateResourceException;
import com.project.commonlib.exception.ResourceNotFoundException;
import com.project.commonlib.payload.request.CityRequest;
import com.project.commonlib.payload.response.CityResponse;
import com.project.locationservice.mapper.CityMapper;
import com.project.locationservice.modal.City;
import com.project.locationservice.repository.CityRepository;
import com.project.locationservice.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    @Transactional
    public CityResponse createCity(CityRequest cityRequest) {
        log.info("Creating a new city with code: {}", cityRequest.getCityCode());

        if (cityRepository.existsByCityCode(cityRequest.getCityCode())) {
            throw new DuplicateResourceException("City code " + cityRequest.getCityCode() + " already exists.");
        }

        City city = cityMapper.toEntity(cityRequest);
        City savedCity = cityRepository.save(city);
        return cityMapper.toResponse(savedCity);
    }

    @Override
    @Transactional(readOnly = true)
    public CityResponse getCityById(Long id) {
        log.info("Fetching city with ID: {}", id);
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id));
        return cityMapper.toResponse(city);
    }

    @Override
    @Transactional(readOnly = true)
    public CityResponse getCityByCode(String cityCode) {
        log.info("Fetching city with code: {}", cityCode);
        City city = cityRepository.findByCityCode(cityCode)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with code: " + cityCode));
        return cityMapper.toResponse(city);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        log.info("Fetching paginated cities for country code: {}", countryCode);
        return cityRepository.findByCountryCode(countryCode, pageable)
                .map(cityMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        log.info("Searching cities with keyword: {}", keyword);
        return cityRepository.searchCities(keyword, pageable)
                .map(cityMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityResponse> getAllCities(Pageable pageable) {
        log.info("Fetching all paginated cities");
        return cityRepository.findAll(pageable)
                .map(cityMapper::toResponse);
    }

    @Override
    @Transactional
    public CityResponse updateCity(Long id, CityRequest cityRequest) {
        log.info("Updating city with ID: {}", id);
        City existingCity = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id));

        if (!existingCity.getCityCode().equalsIgnoreCase(cityRequest.getCityCode())
                && cityRepository.existsByCityCode(cityRequest.getCityCode())) {
            throw new DuplicateResourceException("City code " + cityRequest.getCityCode() + " is already taken.");
        }

        existingCity.setCityCode(cityRequest.getCityCode());
        existingCity.setCountryCode(cityRequest.getCountryCode());
        existingCity.setName(cityRequest.getName());
        existingCity.setCountry(cityRequest.getCountry());
        existingCity.setTimeZone(cityRequest.getTimeZone());
        existingCity.setRegionCode(cityRequest.getRegionCode());

        City updatedCity = cityRepository.save(existingCity);
        return cityMapper.toResponse(updatedCity);
    }

    @Override
    @Transactional
    public void deleteCity(Long id) {
        log.info("Deleting city with ID: {}", id);
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("City not found with id: " + id);
        }
        cityRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsCityByCode(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }

    @Override
    public boolean validateCityCode(String cityCode) {
        return cityCode != null && cityCode.matches("^[A-Z]{3}$");
    }

}