package br.com.edielsonassis.controller.swagger;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;

import br.com.edielsonassis.dto.CambioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cambio", description = "Endpoints for Managing Currency Exchange")
public interface CambioControllerSwagger {

    @Operation(
        summary = "Finds a Cambio Rate",
        description = "Retrieve the exchange rate for a given currency pair and amount.",
        tags = {"Cambio"},
        parameters = {
            @Parameter(name = "amount", description = "The amount to convert", required = true, example = "100.00"),
            @Parameter(name = "from", description = "The source currency (3-letter ISO code)", required = true, example = "USD"),
            @Parameter(name = "to", description = "The target currency (3-letter ISO code)", required = true, example = "EUR")
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", 
                content = @Content(schema = @Schema(implementation = CambioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Unexpected error occurred", content = @Content)
        }
    )
    ResponseEntity<CambioResponse> getCambio(BigDecimal amount, String from, String to);
}