package br.com.edielsonassis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edielsonassis.controller.swagger.AuthControllerSwagger;
import br.com.edielsonassis.dtos.UserRequest;
import br.com.edielsonassis.dtos.UserResponse;
import br.com.edielsonassis.security.TokenJWT;
import br.com.edielsonassis.service.AuthService;

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Retry(name = "auth-service")
@AllArgsConstructor
@RestController
@RequestMapping(path = "auth-service")
public class AuthController implements AuthControllerSwagger {

    private final AuthService service;
    
    @PostMapping(path = "/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody UserRequest userRequest) {
        var user = service.signup(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
	
	@PostMapping(path = "/signin")	
    public ResponseEntity<TokenJWT> signin(@Valid @RequestBody UserRequest userRequest) {
		var token = service.signin(userRequest);
		return ResponseEntity.ok(token);
	}
	
	@PatchMapping(path = "/{email}/{oldPassword}/{password}")
	public ResponseEntity<String> updateUserPassword(@PathVariable String email, @PathVariable String oldPassword, @PathVariable String password) {
		var user = service.updateUserPassword(email, password, oldPassword);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{email}")
	public ResponseEntity<Void> deleteUser(@PathVariable String email) {
		service.deleteUser(email);
		return ResponseEntity.noContent().build();
	}
}