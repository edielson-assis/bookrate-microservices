package br.com.edielsonassis.controller.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.edielsonassis.services.exceptions.CurrencyConverterException;
import br.com.edielsonassis.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CurrencyConverterException.class)
    public ResponseEntity<StandardError> badRequest(CurrencyConverterException exception, HttpServletRequest request) {
        String error = "Invalid request";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> notFound(ObjectNotFoundException exception, HttpServletRequest request) {
        String error = "Not Found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(JWTVerificationException.class)
	public ResponseEntity<StandardError> jwtVerificationExcpetionHandler(SecurityException exception, HttpServletRequest request) {
        String error = "Access denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardError> accessDeniedExcpetionHandler(AccessDeniedException exception, HttpServletRequest request) {
        String error = "Access denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

    @ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<StandardError> validationExcpetionHandler(IllegalStateException exception, HttpServletRequest request) {
        String error = "Invalid request";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> validationExcpetionHandler(IllegalArgumentException exception, HttpServletRequest request) {
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