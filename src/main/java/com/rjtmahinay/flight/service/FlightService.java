package com.rjtmahinay.flight.service;

import com.rjtmahinay.flight.exception.FlightNotFoundException;
import com.rjtmahinay.flight.model.Flight;
import com.rjtmahinay.flight.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public Flux<Flight> searchFlights(String origin, String destination, LocalDateTime date) {
        if (date == null) {
            return flightRepository.findByOriginAndDestination(origin, destination);
        }
        // Find flights for the same day
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return flightRepository.findByOriginAndDestinationAndDepartureTimeBetween(
            origin, destination, startOfDay, endOfDay);
    }

    public Flux<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    
    public Mono<Flight> getFlightById(Long id) {
        return flightRepository.findById(id)
                .switchIfEmpty(Mono.error(new FlightNotFoundException("Flight not found with id: " + id)));
    }
    
    public Mono<Flight> saveFlight(Flight flight) {
        if (flight.getId() != null) {
            return Mono.error(new IllegalArgumentException("A new flight cannot already have an ID"));
        }
        return flightRepository.save(flight);
    }
    
    public Mono<Flight> updateFlight(Long id, Flight flight) {
        return flightRepository.findById(id)
                .flatMap(existingFlight -> {
                    existingFlight.setFlightNumber(flight.getFlightNumber());
                    existingFlight.setOrigin(flight.getOrigin());
                    existingFlight.setDestination(flight.getDestination());
                    existingFlight.setDepartureTime(flight.getDepartureTime());
                    existingFlight.setArrivalTime(flight.getArrivalTime());
                    existingFlight.setAvailableSeats(flight.getAvailableSeats());
                    existingFlight.setPrice(flight.getPrice());
                    existingFlight.setStatus(flight.getStatus());
                    return flightRepository.save(existingFlight);
                });
    }
    
    public Mono<Void> deleteFlight(Long id) {
        return flightRepository.findById(id)
                .flatMap(flightRepository::delete)
                .switchIfEmpty(Mono.error(new FlightNotFoundException("Flight not found with id: " + id)));
    }
    
    public Flux<Flight> searchFlightsByNumberAndDate(String flightNumber, LocalDateTime date) {
        if (date == null) {
            return flightRepository.findByFlightNumber(flightNumber);
        }
        // Find flights for the same day
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return flightRepository.findByFlightNumberAndDepartureTimeBetween(
            flightNumber, startOfDay, endOfDay);
    }
}
