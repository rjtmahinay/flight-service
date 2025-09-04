package com.rjtmahinay.flight.config;

import com.rjtmahinay.flight.model.Flight;
import com.rjtmahinay.flight.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Profile("!test") // Don't run this in tests
public class DataInitializer implements CommandLineRunner {

    private final FlightRepository flightRepository;

    @Override
    public void run(String... args) {
        // Clear existing data
        flightRepository.deleteAll()
                .thenMany(
                        // Add sample flights
                        flightRepository.save(Flight.builder()
                                .airlineName("United Airlines")
                                .flightNumber("UA123")
                                .origin("JFK")
                                .destination("LAX")
                                .departureTime(LocalDateTime.now().plusDays(1))
                                .arrivalTime(LocalDateTime.now().plusDays(1).plusHours(6))
                                .availableSeats(150)
                                .price(350.0)
                                .status("SCHEDULED")
                                .build())
                                .then(flightRepository.save(Flight.builder()
                                        .airlineName("American Airlines")
                                        .flightNumber("AA456")
                                        .origin("LAX")
                                        .destination("ORD")
                                        .departureTime(LocalDateTime.now().plusDays(2))
                                        .arrivalTime(LocalDateTime.now().plusDays(2).plusHours(4))
                                        .availableSeats(120)
                                        .price(280.0)
                                        .status("SCHEDULED")
                                        .build()))
                                .then(flightRepository.save(Flight.builder()
                                        .airlineName("Delta Air Lines")
                                        .flightNumber("DL789")
                                        .origin("ORD")
                                        .destination("JFK")
                                        .departureTime(LocalDateTime.now().plusDays(3))
                                        .arrivalTime(LocalDateTime.now().plusDays(3).plusHours(2))
                                        .availableSeats(180)
                                        .price(220.0)
                                        .status("DELAYED")
                                        .build()))
                )
                .subscribe();
    }
}
