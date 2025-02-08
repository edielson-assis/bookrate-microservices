package br.com.edielsonassis.services;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import br.com.edielsonassis.dtos.BookRequest;
import br.com.edielsonassis.dtos.BookResponse;
import br.com.edielsonassis.models.Book;
import br.com.edielsonassis.models.User;
import br.com.edielsonassis.proxy.CambioProxy;
import br.com.edielsonassis.repositories.BookRepository;
import br.com.edielsonassis.services.exceptions.CurrencyConverterException;
import br.com.edielsonassis.services.exceptions.ObjectNotFoundException;
import br.com.edielsonassis.utils.AuthenticatedUser;
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
        book.setUser(currentUser());
        repository.save(book);

        var bookResponse = new BookResponse();
        BeanUtils.copyProperties(book, bookResponse);
        log.info("Creating a new book: {}", bookRequest.getTitle());
        return bookResponse;
    }

    public BookResponse findBookById(UUID bookId, String currency) {
        var book = findBookById(bookId);
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
            log.error("Error converting currency for book ID: {}", bookId);
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
                        log.error("Error converting price for book ID={} to currency={}", book.getBookId(), currency);
                        throw new CurrencyConverterException("Error converting price for book ID=" + book.getBookId());
                    }
                    return bookResponse;
                });
    }

    @Transactional
    public BookResponse updateBook(UUID bookId, BookRequest bookRequest) {
        var book = findBookById(bookId);
        hasPermission(book);
        BeanUtils.copyProperties(bookRequest, book);
        log.info("Updating book with ID: {}", book.getBookId());
        repository.save(book);

        var bookResponse = new BookResponse();
        BeanUtils.copyProperties(book, bookResponse);
        return bookResponse;
    }

    @Transactional
    public void deleteBook(UUID bookId) {
        var book = findBookById(bookId);
        hasPermission(book);
        log.info("Deleting book with ID: {}", book.getBookId());
        repository.delete(book);
    }

    private Book findBookById(UUID bookId) {
        log.info("Searching for book with ID: {}", bookId);
        return repository.findById(bookId).orElseThrow(() -> {
            log.error("Book ID not found: {}", bookId);
            return new ObjectNotFoundException("Book not found");
        });
    }

    private boolean hasPermission(Book book) {
        if (!(currentUser().equals(book.getUser()) || isAdmin(currentUser()))) {
            throw new AccessDeniedException("Access denied");
        }
        return true;
    }

    private User currentUser() {
        return AuthenticatedUser.getCurrentUser();
    }

    private boolean isAdmin(User user) {
        return user.getRole().equals("ADMIN");
    }
}