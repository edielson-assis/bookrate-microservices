package br.com.edielsonassis.services.exceptions;

public class CurrencyConverterException extends RuntimeException {
    
    public CurrencyConverterException(String msg) {
        super(msg);
    }
}