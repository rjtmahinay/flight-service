package com.rjtmahinay.flight.repository;

import com.rjtmahinay.flight.model.Flight;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface FlightRepository extends R2dbcRepository<Flight, Long> {
    Flux<Flight> findByOriginAndDestination(String origin, String destination);
    
    @Query("SELECT * FROM flights WHERE origin = :origin AND destination = :destination " +
           "AND departure_time >= :startDate AND departure_time < :endDate")
    Flux<Flight> findByOriginAndDestinationAndDepartureTimeBetween(
        String origin, String destination, LocalDateTime startDate, LocalDateTime endDate);
    
    Flux<Flight> findByFlightNumber(String flightNumber);
    
    @Query("SELECT * FROM flights WHERE flight_number = :flightNumber " +
           "AND departure_time >= :startDate AND departure_time < :endDate")
    Flux<Flight> findByFlightNumberAndDepartureTimeBetween(
        String flightNumber, LocalDateTime startDate, LocalDateTime endDate);
}
