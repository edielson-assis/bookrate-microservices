package br.com.edielsonassis.controller.swagger;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import br.com.edielsonassis.dto.BookRequest;
import br.com.edielsonassis.dto.BookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Books", description = "Endpoints for Managing Books")
public interface BookControllerSwagger {
    
    @Operation(
        summary = "Adds a new Book",
		description = "Adds a new Book",
		tags = {"Books"},
		responses = {
			@ApiResponse(responseCode = "201", description = "Created book",
				content = @Content(schema = @Schema(implementation = BookResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad request - Something is wrong with the request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Server error", content = @Content)
		}
	)
    ResponseEntity<BookResponse> createBook(BookRequest bookRequest);

	@Operation(
        summary = "Finds a Book", 
		description = "Search for one books by ID",
		tags = {"Books"},
		parameters = {
            @Parameter(name = "id", description = "ID of the book to find", example = "1", required = true),
			@Parameter(name = "currency", description = "Currency to convert the book price to", example = "EUR", required = true) 
        },
		responses = {
			@ApiResponse(responseCode = "200", description = "Success", 
				content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Something is wrong with the request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found - Book not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Server error", content = @Content)
		}
	)
    ResponseEntity<BookResponse> findBookById(Long id, String currency);

    @Operation(
        summary = "Finds all Books", 
		description = "Finds all Books",
		tags = {"Books"},
        parameters = {
            @Parameter(name = "page", description = "Page number for pagination", required = true),
			@Parameter(name = "size", description = "Number of items per page", required = true),
			@Parameter(name = "direction", description = "Sorting direction (asc or desc)", required = true),
			@Parameter(name = "currency", description = "Currency to convert book prices to", required = true)
        },
		responses = {
			@ApiResponse(responseCode = "200", description = "Success", 
				content = @Content(schema = @Schema(implementation = BookResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Server error", content = @Content)
		}
	)
    ResponseEntity<Page<BookResponse>> findAllBooks(Integer page, Integer size, String direction, String currency);
}