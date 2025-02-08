package br.com.edielsonassis.models;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class Cambio implements Serializable {

	private String from;
	private String to;
	private BigDecimal conversionFactor;
	private BigDecimal convertedValue;
}