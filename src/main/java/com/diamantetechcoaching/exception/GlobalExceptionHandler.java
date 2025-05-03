package com.diamantetechcoaching.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Global exception handler for all controllers in the application.
 * Handles 500 series errors and other exceptions, providing a consistent
 * error response format to clients.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());
    
    /**
     * Handles all generic server errors
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex, HttpServletRequest request) {
        logger.log(Level.SEVERE, "Internal server error", ex);
        
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        
        ErrorResponse errorResponse = new ErrorResponse(
            status.value(),
            status.getReasonPhrase(),
            "An unexpected error occurred. Please try again later.",
            request.getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, status);
    }
    
    /**
     * Handles specific ResponseStatusException cases
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(
            ResponseStatusException ex, HttpServletRequest request) {
        
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        
        // Log server errors (500 series)
        if (status.is5xxServerError()) {
            logger.log(Level.SEVERE, "Server error: " + status, ex);
        } else {
            logger.log(Level.INFO, "Client error: " + status, ex);
        }
        
        ErrorResponse errorResponse = new ErrorResponse(
            status.value(),
            status.getReasonPhrase(),
            ex.getReason() != null ? ex.getReason() : "Request could not be processed",
            request.getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, status);
    }
    
    /**
     * Specifically handles 500 series errors
     */
    @ExceptionHandler({
        org.springframework.web.HttpRequestMethodNotSupportedException.class,
        org.springframework.web.HttpMediaTypeNotSupportedException.class,
        org.springframework.beans.ConversionNotSupportedException.class,
        org.springframework.http.converter.HttpMessageNotWritableException.class
    })
    public ResponseEntity<ErrorResponse> handleServerErrors(Exception ex, HttpServletRequest request) {
        logger.log(Level.SEVERE, "Server infrastructure error", ex);
        
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        
        ErrorResponse errorResponse = new ErrorResponse(
            status.value(),
            status.getReasonPhrase(),
            "The server encountered an internal error. Please try again later.",
            request.getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, status);
    }
}

