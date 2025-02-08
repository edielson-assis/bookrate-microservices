package br.com.edielsonassis.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.edielsonassis.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByEmail(String email);

    Optional<User> findUserByEmail(String email);
}