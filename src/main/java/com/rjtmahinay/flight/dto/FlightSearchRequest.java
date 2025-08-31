package com.rjtmahinay.flight.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlightSearchRequest {
    // Getters and Setters
    @NotBlank(message = "Origin is required")
    @Schema(description = "Departure airport code (e.g., JFK)", example = "JFK")
    private String origin;

    @NotBlank(message = "Destination is required")
    @Schema(description = "Arrival airport code (e.g., LHR)", example = "LHR")
    private String destination;

    @NotBlank(message = "Departure date is required")
    @Schema(description = "Date of departure in YYYY-MM-DD format", example = "2025-09-15")
    private String departureDate;

    @Positive(message = "Number of passengers must be positive")
    @Schema(description = "Number of passengers", example = "1")
    private int passengers = 1;

    @Schema(description = "Preferred airline name", example = "United Airlines", nullable = true)
    private String airline;

    @Schema(description = "Class of service (e.g., economy, business)", example = "economy")
    private String classOfService = "economy";

}
