package br.com.edielsonassis.wrapper;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import br.com.edielsonassis.model.Cambio;
import lombok.Getter;

@Getter
public class CambioResponseWrapper {
    
    private Map<String, Cambio> data = new HashMap<>();

    @JsonAnySetter
    public void add(String key, Cambio value) {
        data.put(key, value);
    }
}