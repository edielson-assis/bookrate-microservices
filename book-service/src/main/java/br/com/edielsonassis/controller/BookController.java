package br.com.edielsonassis.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.edielsonassis.controller.swagger.BookControllerSwagger;
import br.com.edielsonassis.dto.BookRequest;
import br.com.edielsonassis.dto.BookResponse;
import br.com.edielsonassis.service.BookService;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Retry(name = "book-service")
@AllArgsConstructor
@RestController
@RequestMapping(path = "book-service")
public class BookController implements BookControllerSwagger {
	
	private final BookService service;

    @PostMapping
	public ResponseEntity<BookResponse> createBook(@Valid BookRequest bookRequest) {
		var book = service.createBook(bookRequest);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}/{currency}")	
	public ResponseEntity<BookResponse> findBookById(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
		var book = service.findBookById(id, currency);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Page<BookResponse>> findAllBooks(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "10") Integer size, 
			@RequestParam(value = "direction", defaultValue = "asc") String direction, 
			@RequestParam(value = "currency", defaultValue = "USD") String currency) {

		var books = service.findAllBooks(page, size, direction, currency);
        return new ResponseEntity<>(books, HttpStatus.OK);
	}
}