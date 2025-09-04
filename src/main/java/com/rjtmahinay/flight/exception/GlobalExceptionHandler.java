package com.rjtmahinay.flight.exception;

//@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(FlightNotFoundException.class)
//    public Mono<ResponseEntity<Map<String, String>>> handleFlightNotFound(FlightNotFoundException ex) {
//        return Mono.just(ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(createErrorResponse(ex.getMessage())));
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public Mono<ResponseEntity<Map<String, String>>> handleIllegalArgument(IllegalArgumentException ex) {
//        return Mono.just(ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(createErrorResponse(ex.getMessage())));
//    }
//
//    @ExceptionHandler(WebExchangeBindException.class)
//    public Mono<ResponseEntity<Map<String, String>>> handleValidationException(WebExchangeBindException ex) {
//        String errorMessage = ex.getFieldErrors().stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .findFirst()
//                .orElse("Validation failed");
//
//        return Mono.just(ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(createErrorResponse(errorMessage)));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public Mono<ResponseEntity<Map<String, String>>> handleAllExceptions(Exception ex) {
//        return Mono.just(ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(createErrorResponse("An unexpected error occurred: " + ex.getMessage())));
//    }
//
//    private Map<String, String> createErrorResponse(String message) {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", message);
//        response.put("status", HttpStatus.BAD_REQUEST.toString());
//        return response;
//    }
}
