package br.com.edielsonassis.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    
    ADMIN("Admin"),
    USER("User");

    private String name;
}