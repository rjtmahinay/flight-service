package com.rjtmahinay.flight.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FlightSearchResponse {
    @Schema(description = "Status of the search operation", example = "Success")
    private String status;
    
    @Schema(description = "Descriptive message about the search results", example = "Flights found.")
    private String message;
    
    @Schema(description = "List of available flights")
    private List<FlightDetails> flights;

    public FlightSearchResponse(String status, String message, List<FlightDetails> flights) {
        this.status = status;
        this.message = message;
        this.flights = flights;
    }

    @Setter
    @Getter
    @Schema(name = "FlightDetails", description = "Details of a single flight")
    public static class FlightDetails {
        // Getters and Setters
        @Schema(description = "Name of the airline", example = "United Airlines")
        private String airlineName;
        
        @Schema(description = "Flight number", example = "UA123")
        private String flightNumber;
        
        @Schema(description = "Scheduled departure time", example = "08:00 AM")
        private String departureTime;
        
        @Schema(description = "Scheduled arrival time", example = "04:30 PM")
        private String arrivalTime;
        
        @Schema(description = "Ticket price in USD", example = "750.00")
        private double price;
        
        @Schema(description = "Total flight duration", example = "8h 30m")
        private String duration;
        
        @Schema(description = "Information about layovers", example = "None")
        private String layovers;

    }
}
