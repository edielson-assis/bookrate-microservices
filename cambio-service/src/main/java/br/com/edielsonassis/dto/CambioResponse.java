package br.com.edielsonassis.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Represents the response for a currency exchange operation")
public class CambioResponse implements Serializable {

    @Schema(description = "The source currency (3-letter ISO code)", example = "USD")
    private String from;

    @Schema(description = "The target currency (3-letter ISO code)", example = "EUR")
    private String to;

    @Schema(description = "The original amount to be converted", example = "100.00")
    private BigDecimal amount;

    @Schema(description = "The conversion factor used for the currency exchange", example = "0.85")
    private BigDecimal conversionFactor;

    @Schema(description = "The converted value in the target currency", example = "85.00")
    private BigDecimal convertedValue;
}