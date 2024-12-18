package br.com.edielsonassis.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.edielsonassis.wrapper.CambioResponseWrapper;

@FeignClient(name = "awesomeapi", url = "https://economia.awesomeapi.com.br/last")
public interface AwesomeAPIService {

    @GetMapping(value = "/{from}-{to}")
	public CambioResponseWrapper getCurrencyConverter(@PathVariable("from") String from, @PathVariable("to") String to);
}