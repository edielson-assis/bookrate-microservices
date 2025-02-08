package br.com.edielsonassis.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Represents a User")
public record UserResponse(
    
    @Schema(description = "Unique identifier of the user", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID userId, 
    
    @Schema(description = "Email of the user.", example = "maria@example.com")
    String email) {}