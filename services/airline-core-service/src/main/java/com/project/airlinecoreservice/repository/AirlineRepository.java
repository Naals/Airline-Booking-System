package com.project.airlinecoreservice.repository;

import com.project.airlinecoreservice.modal.Airline;
import com.project.commonlib.enums.AirlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Optional<Airline> findByIataCode(String iataCode);
    Optional<Airline> findByIcaoCode(String icaoCode);
    List<Airline> findByStatus(AirlineStatus status);
    boolean existsByIataCode(String iataCode);
    boolean existsByIcaoCode(String icaoCode);
}