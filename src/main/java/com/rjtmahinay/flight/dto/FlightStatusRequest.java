package com.rjtmahinay.flight.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Request object for checking flight status by flight number and date
 */
@Setter
@Getter
public class FlightStatusRequest {
    
    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z]{2,3}\\d{1,4}[A-Z]?$",
            message = "Invalid flight number format. Example: AA123 or DL4567")
    @Schema(description = "Flight number (e.g., AA123, DL4567)", example = "AA123")
    private String flightNumber;

    @NotNull(message = "Date and time is required")
    @Schema(description = "Date and time to check flight status", 
            example = "2025-09-10T10:30:00")
    private LocalDateTime date;

    public FlightStatusRequest() {}

    public FlightStatusRequest(String flightNumber, LocalDateTime date) {
        this.flightNumber = flightNumber;
        this.date = date;
    }
}
