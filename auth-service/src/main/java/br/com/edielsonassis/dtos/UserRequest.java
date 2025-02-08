package br.com.edielsonassis.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(description = "Represents the User's Signin and Signup")
public record UserRequest(

@   Schema(description = "Email of the user.", example = "maria@example.com", maxLength = 100, required = true)
    @NotBlank(message = "Email is required") 
    @Email(message = "Invalid email")
    String email, 

    @Schema(description = "Password for the user account.", example = "372@RfI5n&Ml", maxLength = 255, required = true)
    @NotBlank(message = "Password is required")
    String password) {}