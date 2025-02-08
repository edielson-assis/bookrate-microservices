package br.com.edielsonassis.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.edielsonassis.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    boolean existsByEmail(String email);

    UserDetails findByEmail(String email);

    Optional<User> findUserByEmail(String email);
}