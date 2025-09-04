package com.rjtmahinay.flight.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FlightStatusResponse {
    @Schema(description = "Status of the status check operation", example = "Success")
    private String status;
    
    @Schema(description = "Descriptive message about the status check results", example = "Flight status retrieved successfully.")
    private String message;
    
    @Schema(description = "List of flight statuses")
    private List<FlightStatus> flights;

    public FlightStatusResponse(String status, String message, List<FlightStatus> flights) {
        this.status = status;
        this.message = message;
        this.flights = flights;
    }

    @Setter
    @Getter
    @Schema(name = "FlightStatus", description = "Status information for a single flight")
    public static class FlightStatus {
        @Schema(description = "Name of the airline", example = "American Airlines")
        private String airlineName;
        
        @Schema(description = "Flight number", example = "AA123")
        private String flightNumber;
        
        @Schema(description = "Scheduled departure time in 12-hour format", example = "08:30 AM")
        private String departureTime;
        
        @Schema(description = "Scheduled departure date in YYYY-MM-DD format", example = "2025-09-10")
        private String departureDate;
        
        @Schema(description = "Scheduled arrival time in 12-hour format", example = "11:45 AM")
        private String arrivalTime;
        
        @Schema(description = "Scheduled arrival date in YYYY-MM-DD format", example = "2025-09-10")
        private String arrivalDate;
        
        @Schema(description = "Ticket price in USD", example = "450.00")
        private double price;
        
        @Schema(description = "Total flight duration", example = "3h 15m")
        private String duration;
        
        @Schema(description = "Information about layovers", example = "None")
        private String layovers;
        
        @Schema(description = "Current flight status", example = "On Time", 
                allowableValues = {"On Time", "Delayed", "Cancelled", "Boarding", "Departed", "Arrived"})
        private String status;
        
        @Schema(description = "Delay duration if flight is delayed", example = "45 min", nullable = true)
        private String delay;
    }
}
