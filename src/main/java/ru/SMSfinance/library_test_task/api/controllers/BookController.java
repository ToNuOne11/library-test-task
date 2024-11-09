package ru.SMSfinance.library_test_task.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.SMSfinance.library_test_task.api.dto.AckDto;
import ru.SMSfinance.library_test_task.api.dto.BookDto;
import ru.SMSfinance.library_test_task.api.factories.BookDtoFactory;
import ru.SMSfinance.library_test_task.api.services.BookService;
import ru.SMSfinance.library_test_task.store.entities.BookEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    public static final String GET_ALL_BOOKS = "/api/books";
    public static final String GET_BOOK_BY_ID = "/api/books/{book_id}";
    public static final String CREATE_BOOK = "/api/books";
    public static final String UPDATE_BOOK = "/api/books/{book_id}";
    public static final String DELETE_BOOK = "/api/books/{book_id}";

    @GetMapping(GET_ALL_BOOKS)
    public List<BookDto> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping(GET_BOOK_BY_ID)
    public BookDto getBookById(@PathVariable Long book_id) {
        return bookService.findBookById(book_id);
    }

    @PostMapping(CREATE_BOOK)
    public BookDto createBook(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "published_date") LocalDate publishedDate) {
        return bookService.createBook(title, author, publishedDate);
    }

    @PatchMapping(UPDATE_BOOK)
    public BookDto updateBook(
            @PathVariable Long book_id,
            @RequestParam(name = "title", required = false) Optional<String> optionalTitle,
            @RequestParam(name = "author", required = false) Optional<String> optionalAuthor,
            @RequestParam(name = "published_date", required = false) Optional<LocalDate> optionalPublishedDate) {
        return bookService.updateBook(book_id,  optionalTitle,  optionalAuthor,  optionalPublishedDate);
    }

    @DeleteMapping(DELETE_BOOK)
    public AckDto deleteBook(@PathVariable Long book_id){
        return bookService.deleteBook(book_id);
    }
}
