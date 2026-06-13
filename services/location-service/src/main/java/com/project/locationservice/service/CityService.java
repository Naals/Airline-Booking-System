package com.project.locationservice.service;

import com.project.commonlib.payload.request.CityRequest;
import com.project.commonlib.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CityService {

    CityResponse createCity(CityRequest cityRequest);

    CityResponse getCityById(Long id);

    CityResponse getCityByCode(String cityCode);

    Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable);

    Page<CityResponse> searchCities(String keyword, Pageable pageable);

    Page<CityResponse> getAllCities(Pageable pageable);

    CityResponse updateCity(Long id, CityRequest cityRequest);

    void deleteCity(Long id);

    boolean existsCityByCode(String cityCode);
    boolean validateCityCode(String cityCode);
}