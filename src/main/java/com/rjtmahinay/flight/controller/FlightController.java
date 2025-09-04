package com.rjtmahinay.flight.controller;

import com.rjtmahinay.flight.dto.FlightStatusRequest;
import com.rjtmahinay.flight.dto.FlightStatusResponse;
import com.rjtmahinay.flight.model.Flight;
import com.rjtmahinay.flight.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Tag(name = "Flight Search API", description = "Reactive API for searching and managing flight details.")
public class FlightController {

    private final FlightService flightService;

    @Operation(summary = "Search for available flights",
            description = "Searches for available flights based on origin, destination, and date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved flight data"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    @GetMapping("/search")
    public Flux<Flight> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
                
        return flightService.searchFlights(origin, destination, date);
    }
    
    @Operation(summary = "Get all flights", 
              description = "Retrieves all available flights.")
    @GetMapping
    public Flux<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }
    
    @Operation(summary = "Get flight by ID")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Flight>> getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Create a new flight")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Flight> createFlight(@Valid @RequestBody Flight flight) {
        return flightService.saveFlight(flight);
    }
    
    @Operation(summary = "Update an existing flight")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Flight>> updateFlight(
            @PathVariable Long id, @Valid @RequestBody Flight flight) {
        return flightService.updateFlight(id, flight)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Delete a flight")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteFlight(@PathVariable Long id) {
        return flightService.deleteFlight(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Check flight status",
            description = "Retrieves the current status of a specific flight by flight number and date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved flight status"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @PostMapping("/status")
    public Mono<ResponseEntity<FlightStatusResponse>> checkFlightStatus(
            @Valid @RequestBody FlightStatusRequest request) {
        
        return flightService.searchFlightsByNumberAndDate(
                request.getFlightNumber(), 
                request.getDate() != null ? request.getDate() : null)
            .collectList()
            .flatMap(flights -> {
                if (flights.isEmpty()) {
                    FlightStatusResponse response = new FlightStatusResponse();
                    response.setStatus("Error");
                    response.setMessage("Flight not found.");
                    response.setFlights(List.of());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
                }
                
                List<FlightStatusResponse.FlightStatus> statuses = flights.stream()
                    .map(flight -> {
                        FlightStatusResponse.FlightStatus status = new FlightStatusResponse.FlightStatus();
                        status.setAirlineName(flight.getAirlineName() != null ? flight.getAirlineName() : "Unknown Airline");
                        status.setFlightNumber(flight.getFlightNumber());
                        status.setDepartureTime(flight.getDepartureTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
                        status.setArrivalTime(flight.getArrivalTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
                        status.setDepartureDate(flight.getDepartureTime().toLocalDate().toString());
                        status.setStatus(flight.getStatus());
                        
                        // Calculate duration
                        Duration duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
                        long hours = duration.toHours();
                        long minutes = duration.minusHours(hours).toMinutes();
                        status.setDuration(String.format("%dh %02dm", hours, minutes));
                        
                        // Set default values for other required fields
                        status.setLayovers("None");
                        status.setDelay(flight.getStatus().equals("Delayed") ? "30 min" : null);
                        
                        return status;
                    })
                    .toList();
                
                FlightStatusResponse response = new FlightStatusResponse();
                response.setStatus("Success");
                response.setMessage("Flight status retrieved successfully.");
                response.setFlights(statuses);
                
                return Mono.just(ResponseEntity.ok(response));
            });
    }
}
