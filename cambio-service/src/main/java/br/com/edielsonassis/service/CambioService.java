package br.com.edielsonassis.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.edielsonassis.dto.CambioResponse;
import br.com.edielsonassis.mapper.CambioMapper;
import br.com.edielsonassis.model.Cambio;
import br.com.edielsonassis.service.exception.CurrencyConverterException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class CambioService {
    
    private final AwesomeAPIService service;

    public CambioResponse getCambio(BigDecimal amount, String from, String to) {
        log.info("Initiating currency conversion: amount={}, from={}, to={}", amount, from, to);
        Cambio cambio = null;
        from = from.toUpperCase();
        to = to.toUpperCase();

        currencyValidate(amount, from, to);
        try {  
            var responseWrapper = service.getCurrencyConverter(from, to);
            log.debug("Received response from AwesomeAPI: {}", responseWrapper);
            cambio = responseWrapper.getData().get(from + to);
        } catch (Exception e) {
            log.error("Unexpected error during currency conversion: {}", e.getMessage());
            throw new CurrencyConverterException("Error converting currency");
        }
        log.info("Currency conversion successful: {} -> {} = {}", from, to, cambio.getBid());
        return CambioMapper.toDto(amount, cambio);
    }

    private void currencyValidate(BigDecimal amount, String from, String to) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Validation failed: Amount must be greater than zero, received: {}", amount);
            throw new CurrencyConverterException("Amount must be greater than zero");
        }
        validateInputData(from);
        validateInputData(to);
    }  

    private void validateInputData(String data) {
        if (data == null || data.isBlank() || data.length() != 3 || !data.matches("^[a-zA-Z]+$")) {
            log.error("Validation failed for currency code: {}", data);
            throw new CurrencyConverterException("The type of currency must contain exactly 3 letters");
        }
    }
}