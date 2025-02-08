package br.com.edielsonassis.dtos;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEventRequest {
    
    private UUID userId;
	private String email;
	private String role;
	private String actionType;
}