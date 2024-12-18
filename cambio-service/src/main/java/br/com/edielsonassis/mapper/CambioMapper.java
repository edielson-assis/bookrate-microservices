package br.com.edielsonassis.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.edielsonassis.dto.CambioResponse;
import br.com.edielsonassis.model.Cambio;

public class CambioMapper {
    
    private CambioMapper() {}

    public static CambioResponse toDto(BigDecimal amount, Cambio cambio) {
        CambioResponse cambioDto = new CambioResponse();
        cambioDto.setFrom(cambio.getCode());
        cambioDto.setTo(cambio.getCodein());
        cambioDto.setAmount(amount.setScale(2));
        cambioDto.setConversionFactor(new BigDecimal(cambio.getBid()));
        cambioDto.setConvertedValue(exchangeRate(amount, cambio));
        return cambioDto;
    }

    private static BigDecimal exchangeRate(BigDecimal amount, Cambio cambio) {
        return amount.multiply(new BigDecimal(cambio.getBid())).setScale(2, RoundingMode.HALF_UP);
    }
}