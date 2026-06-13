package com.project.locationservice.repository;

import com.project.locationservice.modal.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByCityCode(String cityCode);

    Page<City> findByCountryCode(String countryCode, Pageable pageable);

    boolean existsByCityCode(String cityCode);

    @Query("SELECT c FROM City c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.country) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.cityCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.countryCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.regionCode) LIKE LOWER(CONCAT('%', :keyword, '%'))" )
    Page<City> searchCities(@Param("keyword") String keyword, Pageable pageable);
}