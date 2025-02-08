package br.com.edielsonassis.controller.swagger;

import org.springframework.http.ResponseEntity;

import br.com.edielsonassis.dtos.UserRequest;
import br.com.edielsonassis.dtos.UserResponse;
import br.com.edielsonassis.security.TokenJWT;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Endpoints for Managing User")
public interface AuthControllerSwagger {
    
    static final String SECURITY_SCHEME_KEY = "bearer-key";
    
    @Operation(
        summary = "Adds a new user",
		description = "Adds a new user",
		tags = {"Authentication"},
		responses = {
			@ApiResponse(responseCode = "201", description = "Created user",
				content = @Content(schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad request - Something is wrong with the request", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem",  content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Server error", content = @Content)
		}
	)
    ResponseEntity<UserResponse> signup(UserRequest userRequest);

    @Operation(
        summary = "Authenticates a user",
		description = "Authenticates a user and returns a token",
		tags = {"Authentication"},
		responses = {
			@ApiResponse(responseCode = "200", description = "Authenticated user",
				content = @Content(schema = @Schema(implementation = TokenJWT.class))),
			@ApiResponse(responseCode = "400", description = "Bad request - Something is wrong with the request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid email or password", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Server error", content = @Content)
		}
	)
    ResponseEntity<TokenJWT> signin(UserRequest userRequest);

    @Operation(
		security = {@SecurityRequirement(name = SECURITY_SCHEME_KEY)},
        summary = "Updates a user password",
		description = "Updates a user password",
		tags = {"Authentication"},
		responses = {
			@ApiResponse(responseCode = "204", description = "Updated user password", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem",  content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found - User not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Server error", content = @Content)
		}
	)
    ResponseEntity<String> updateUserPassword(String email, String oldPassword, String password);

    @Operation(
		security = {@SecurityRequirement(name = SECURITY_SCHEME_KEY)},
        summary = "Deletes a user",
		description = "Deletes a user by their email",
		tags = {"Authentication"},
		responses = {
			@ApiResponse(responseCode = "204", description = "Deleted user", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem",  content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found - User not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Server error", content = @Content)
		}
	)
    ResponseEntity<Void> deleteUser(String email);
}