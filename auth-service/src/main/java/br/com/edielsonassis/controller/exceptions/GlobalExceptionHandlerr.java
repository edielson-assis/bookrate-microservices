package br.com.edielsonassis.controller.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.edielsonassis.service.exceptions.ObjectNotFoundException;
import br.com.edielsonassis.service.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandlerr {

	@ExceptionHandler(JWTCreationException.class)
	public ResponseEntity<ExceptionResponse> jwtCreationExcpetionHandler(SecurityException exception, HttpServletRequest request) {
        String error = "Access denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

	@ExceptionHandler(JWTVerificationException.class)
	public ResponseEntity<ExceptionResponse> jwtVerificationExcpetionHandler(SecurityException exception, HttpServletRequest request) {
        String error = "Access denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionResponse> accessDeniedExcpetionHandler(AccessDeniedException exception, HttpServletRequest request) {
        String error = "Access denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ExceptionResponse> usernameNotFoundExcpetionHandler(ObjectNotFoundException exception, HttpServletRequest request) {
        String error = "Not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ExceptionResponse> ojectNotFoundExcpetionHandler(ObjectNotFoundException exception, HttpServletRequest request) {
        String error = "Not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ExceptionResponse> validationExcpetionHandler(ValidationException exception, HttpServletRequest request) {
        String error = "Validation error";
        HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ExceptionResponse> validationExcpetionHandler(IllegalStateException exception, HttpServletRequest request) {
        String error = "Invalid request";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponse> validationExcpetionHandler(IllegalArgumentException exception, HttpServletRequest request) {
        String error = "Invalid request";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionResponse> badCredentialsExcpetionHandler(BadCredentialsException exception, HttpServletRequest request) {
        String error = "Invalid credentials";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ExceptionResponse> authenticationExcpetionHandler(AuthenticationException exception, HttpServletRequest request) {
        String error = "Authentication failure";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

  	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> defaultExcpetionHandler(Exception exception, HttpServletRequest request) {
        String error = "Internal server error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(errors(status, error, exception, request));
    }

  	private ExceptionResponse errors(HttpStatus status, String error, Exception message, HttpServletRequest request) {
        return new ExceptionResponse(Instant.now(), status.value(), error, message.getMessage(), request.getRequestURI());
    }
}