package com.rjtmahinay.flight.controller;

import com.rjtmahinay.flight.dto.FlightSearchRequest;
import com.rjtmahinay.flight.dto.FlightSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@Tag(name = "Flight Search Agent", description = "Mock API for searching flight details.")
public class FlightSearchController {

    @Operation(summary = "Search for available flights",
            description = "Mocks a flight search API, returning a list of available flights or a no-results message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved flight data"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    @PostMapping("/search")
    public Mono<ResponseEntity<FlightSearchResponse>> searchFlights(
            @Valid @RequestBody FlightSearchRequest request) {

        // Simulate a "no results" scenario for a specific route to test agent behavior.
        if ("SFO".equalsIgnoreCase(request.getOrigin()) && "ORD".equalsIgnoreCase(request.getDestination())) {
            return Mono.just(ResponseEntity.ok(
                    new FlightSearchResponse("NoResults", 
                                          "No flights were found for the specified route and dates.",
                                          new ArrayList<>())));
        }

        // Create mock flight data
        List<FlightSearchResponse.FlightDetails> mockFlights = new ArrayList<>();
        
        // Flight 1
        FlightSearchResponse.FlightDetails flight1 = new FlightSearchResponse.FlightDetails();
        flight1.setAirlineName("United Airlines");
        flight1.setFlightNumber("UA123");
        flight1.setDepartureTime("08:00 AM");
        flight1.setArrivalTime("04:30 PM");
        flight1.setPrice(750.00);
        flight1.setDuration("8h 30m");
        flight1.setLayovers("None");
        mockFlights.add(flight1);
        
        // Flight 2
        FlightSearchResponse.FlightDetails flight2 = new FlightSearchResponse.FlightDetails();
        flight2.setAirlineName("American Airlines");
        flight2.setFlightNumber("AA456");
        flight2.setDepartureTime("10:15 AM");
        flight2.setArrivalTime("06:45 PM");
        flight2.setPrice(820.00);
        flight2.setDuration("8h 30m");
        flight2.setLayovers("1 (JFK)");
        mockFlights.add(flight2);

        // Simulate filtering by airline
        if (request.getAirline() != null && !request.getAirline().isEmpty()) {
            mockFlights = mockFlights.stream()
                    .filter(flight -> flight.getAirlineName().equalsIgnoreCase(request.getAirline()))
                    .toList();
        }

        return Mono.just(ResponseEntity.ok(
                new FlightSearchResponse("Success", "Flights found.", mockFlights)));
    }
}
