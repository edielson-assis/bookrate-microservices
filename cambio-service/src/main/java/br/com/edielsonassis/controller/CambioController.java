package br.com.edielsonassis.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edielsonassis.controller.swagger.CambioControllerSwagger;
import br.com.edielsonassis.dto.CambioResponse;
import br.com.edielsonassis.service.CambioService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "cambio-service")
public class CambioController implements CambioControllerSwagger {
    
	private final CambioService service;
	
	@Retry(name = "cambio-service")
	@GetMapping(value = "/{amount}/{from}/{to}")
	public ResponseEntity<CambioResponse> getCambio(@PathVariable("amount") BigDecimal amount, @PathVariable("from") String from, @PathVariable("to") String to) {
		var cambio = service.getCambio(amount, from, to);
		return new ResponseEntity<>(cambio, HttpStatus.OK);
	}
}