package br.com.edielsonassis.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.edielsonassis.dto.BookRequest;
import br.com.edielsonassis.dto.BookResponse;
import br.com.edielsonassis.model.Book;
import br.com.edielsonassis.proxy.CambioProxy;
import br.com.edielsonassis.repository.BookRepository;
import br.com.edielsonassis.service.exceptions.CurrencyConverterException;
import br.com.edielsonassis.service.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookService {

    @Value("${bookservice.type.currency}")
    private String typeCurrency;

    private final BookRepository repository;
    private final CambioProxy proxy;

    public BookService(BookRepository repository, CambioProxy proxy) {
        this.repository = repository;
        this.proxy = proxy;
    }

    @Transactional
    public BookResponse createBook(BookRequest bookRequest) {
        var book = new Book();
        BeanUtils.copyProperties(bookRequest, book);
        repository.save(book);

        var bookResponse = new BookResponse();
        BeanUtils.copyProperties(book, bookResponse);
        log.info("Creating a new book: {}", bookRequest.getTitle());
        return bookResponse;
    }

    public BookResponse findBookById(Long id, String currency) {
        var book = findBookById(id);
        try {
            if (!currency.equalsIgnoreCase(typeCurrency)) {
                log.info("Currency conversion requested: from {} to {}", typeCurrency, currency);
                var cambio = proxy.getCambio(book.getPrice(), typeCurrency, currency);
                book.setPrice(cambio.getConvertedValue());
            }
            var bookResponse = new BookResponse();
            BeanUtils.copyProperties(book, bookResponse);
            return bookResponse;
        } catch (Exception e) {
            log.error("Error converting currency for book ID: {}", id);
            throw new CurrencyConverterException("Error converting currency");
        }
    }

    public Page<BookResponse> findAllBooks(Integer page, Integer size, String direction, String currency) {
        log.info("Searching all books with page={}, size={}, direction={}, currency={}", page, size, direction, currency);
    
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        var pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
    
        return repository.findAll(pageable)
                .map(book -> {
                    var bookResponse = new BookResponse();
                    BeanUtils.copyProperties(book, bookResponse);
    
                    try {
                        if (!currency.equalsIgnoreCase(typeCurrency)) {
                            log.info("Currency conversion requested: from {} to {}", typeCurrency, currency);
                            var cambio = proxy.getCambio(book.getPrice(), typeCurrency, currency);
                            bookResponse.setPrice(cambio.getConvertedValue());
                        } 
                    } catch (Exception e) {
                        log.error("Error converting price for book ID={} to currency={}", book.getId(), currency);
                        throw new CurrencyConverterException("Error converting price for book ID=" + book.getId());
                    }
                    return bookResponse;
                });
    }
    
    private Book findBookById(Long id) {
        log.info("Searching for book with ID: {}", id);
        return repository.findById(id).orElseThrow(() -> {
            log.error("Book ID not found: {}", id);
            return new ObjectNotFoundException("Book not found");
        });
    }
}