package br.com.edielsonassis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edielsonassis.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{}