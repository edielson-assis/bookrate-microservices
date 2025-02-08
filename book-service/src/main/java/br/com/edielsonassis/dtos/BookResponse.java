package br.com.edielsonassis.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookResponse implements Serializable {
    
    @Schema(description = "Unique identifier of the book", example = "1")
    private UUID bookId;

    @Schema(description = "Name of the author", example = "Robert C. Martin")
    private String author;

    @Schema(description = "The date the book was launched", example = "2008-08-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate launchDate;

    @Schema(description = "Price of the book", example = "10.15")
    private BigDecimal price;

    @Schema(description = "Title of the book", example = "Clean Code: A Handbook of Agile Software Craftsmanship")
    private String title;

    @Schema(description = "Description of the book", example = "A book that teaches software developers how to write clean, maintainable, and efficient code.")
    private String description;
}