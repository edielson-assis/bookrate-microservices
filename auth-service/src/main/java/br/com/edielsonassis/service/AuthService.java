package br.com.edielsonassis.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.edielsonassis.dtos.UserRequest;
import br.com.edielsonassis.dtos.UserResponse;
import br.com.edielsonassis.mapper.UserMapper;
import br.com.edielsonassis.model.User;
import br.com.edielsonassis.model.enums.ActionType;
import br.com.edielsonassis.model.enums.Role;
import br.com.edielsonassis.publishers.UserEventPublisher;
import br.com.edielsonassis.repository.UserRepository;
import br.com.edielsonassis.security.TokenJWT;
import br.com.edielsonassis.security.TokenService;
import br.com.edielsonassis.service.exceptions.ObjectNotFoundException;
import br.com.edielsonassis.service.exceptions.ValidationException;
import br.com.edielsonassis.utils.component.AuthenticatedUser;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final UserEventPublisher eventPublisher;

    @Transactional
    public UserResponse signup(UserRequest userRequest) {
        User user = UserMapper.toEntity(userRequest);
        validateEmailNotExists(user);
        encryptPassword(user);
        user.setRole(Role.USER);
        log.info("Registering a new User");
        var userResponse = UserMapper.toDto(repository.save(user));
        publishUserEvent(user, ActionType.CREATE);
        return userResponse;
    }

    public TokenJWT signin(UserRequest user) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(user.email(), user.password());
            var authentication = manager.authenticate(authenticationToken);
            
            var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
            log.info("Authenticated User: {}", user.email());
            return new TokenJWT(tokenJWT);
        } catch (InternalAuthenticationServiceException e) {
            log.error("Invalid email or password");
            throw new BadCredentialsException("Invalid email or password");
        }
	}

    @Transactional
    public String updateUserPassword(String email, String password, String oldPassword) {
        var user = AuthenticatedUser.getCurrentUser();
        validateUserEmail(user, email);
		user = findUserByEmail(email);
        if (!encoder.matches(oldPassword, user.getPassword())) {
            log.error("Old password does not match");
            throw new ValidationException("Old password does not match");
        }
        user.setPassword(password);
        encryptPassword(user);
        log.info("Password updated successfully");
        repository.save(user);
        return "Password updated successfully";
    }

    @Transactional
    public void deleteUser(String email) {
        var user = AuthenticatedUser.getCurrentUser();
        validateUserEmail(user, email);
        user = findUserByEmail(email);
        log.info("Deleting user with email: {}", email);
        repository.delete(user);
        publishUserEvent(user, ActionType.DELETE);
	}

    private synchronized void validateEmailNotExists(User user) {
		log.info("Verifying the user's email: {}", user.getEmail());
        var exists = repository.existsByEmail(user.getEmail());
        if (exists) {
            log.error("Email already exists: {}", user.getEmail());
            throw new ValidationException("Email already exists");
        }
    }

    private void encryptPassword(User user) {
		log.info("Encrypting password");
        user.setPassword(encoder.encode(user.getPassword()));
    }

    private User findUserByEmail(String email) {
        log.info("Verifying the user's email: {}", email);
        return repository.findUserByEmail(email).orElseThrow(() -> {
            log.error("Username not found: {}", email);
            return new ObjectNotFoundException("Username not found: " + email);
        });    
    }

    private void validateUserEmail(User user, String email) {
        if (!user.getEmail().equals(email)) {
            log.error("Request failure");
            throw new AccessDeniedException("Request failure");
        }
    }

    private void publishUserEvent(User user, ActionType actionType) {
        log.info("Publishing event");
        var userEvent = UserMapper.toEventDto(user);
        eventPublisher.publishUserEvent(userEvent, actionType);
    }
}