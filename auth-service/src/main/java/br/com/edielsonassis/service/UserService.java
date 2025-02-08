package br.com.edielsonassis.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.edielsonassis.repository.UserRepository;
import br.com.edielsonassis.service.exceptions.ObjectNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Configuration
public class UserService implements UserDetailsService {
    
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            log.info("Verifying the user's email: {}", email);
            return repository.findByEmail(email);   
        } catch (UsernameNotFoundException e) {
            log.error("Username not found: {}", email);
            throw new ObjectNotFoundException("Username not found: " + email);
        }    
    } 
}