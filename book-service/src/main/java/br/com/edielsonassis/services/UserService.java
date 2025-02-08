package br.com.edielsonassis.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.edielsonassis.models.User;
import br.com.edielsonassis.repositories.UserRepository;
import br.com.edielsonassis.services.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        log.info("Registering user data: {}", user.getUsername());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(UUID userId) {
        var user = findById(userId);
        log.info("Deleting user with id: {}", user.getUserId());
        userRepository.delete(user);
    }   

    private User findById(UUID userId) {
        log.info("Verifying the user's Id: {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> {
            log.error("User id not found: {}", userId);
            return new ObjectNotFoundException("User id not found: " + userId);
        }); 
    }
}