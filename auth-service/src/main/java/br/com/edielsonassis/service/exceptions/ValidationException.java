package br.com.edielsonassis.service.exceptions;

public class ValidationException extends RuntimeException {
    
    public ValidationException(String msg) {
        super(msg);
    }
}