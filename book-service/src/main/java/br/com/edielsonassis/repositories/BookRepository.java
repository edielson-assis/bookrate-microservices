package br.com.edielsonassis.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edielsonassis.models.Book;

public interface BookRepository extends JpaRepository<Book, UUID>{}