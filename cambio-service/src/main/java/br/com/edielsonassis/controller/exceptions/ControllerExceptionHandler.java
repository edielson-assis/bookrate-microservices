package br.com.edielsonassis.controller.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.edielsonassis.service.exception.CurrencyConverterException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CurrencyConverterException.class)
    public ResponseEntity<StandardError> badRequest(CurrencyConverterException exception, HttpServletRequest request) {
        String error = "Invalid request";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> serverError(Exception exception, HttpServletRequest request) {
        String error = "Unexpected error occurred";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    private StandardError errors(HttpStatus status, String error, Exception message, HttpServletRequest request) {
        return new StandardError(Instant.now(), status.value(), error, message.getMessage(), request.getRequestURI());
    }
}