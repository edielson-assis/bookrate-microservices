package br.com.edielsonassis.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.edielsonassis.controller.swagger.BookControllerSwagger;
import br.com.edielsonassis.dtos.BookRequest;
import br.com.edielsonassis.dtos.BookResponse;
import br.com.edielsonassis.services.BookService;
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
	
	@GetMapping(value = "/{bookId}/{currency}")	
	public ResponseEntity<BookResponse> findBookById(@PathVariable("bookId") UUID bookId, @PathVariable("currency") String currency) {
		var book = service.findBookById(bookId, currency);
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

	@PutMapping(path = "/{bookId}")
	public ResponseEntity<BookResponse> updateBook(@PathVariable(value = "bookId") UUID bookId, @Valid @RequestBody BookRequest bookRequest) {
		var book = service.updateBook(bookId, bookRequest);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable(value = "bookId") UUID bookId) {
		service.deleteBook(bookId);
		return ResponseEntity.noContent().build();
	}
}