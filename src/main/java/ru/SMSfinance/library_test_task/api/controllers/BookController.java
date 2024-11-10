package ru.SMSfinance.library_test_task.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.SMSfinance.library_test_task.api.dto.AckDto;
import ru.SMSfinance.library_test_task.api.dto.BookDto;
import ru.SMSfinance.library_test_task.api.factories.BookDtoFactory;
import ru.SMSfinance.library_test_task.api.services.BookService;
import ru.SMSfinance.library_test_task.api.services.MessageSender;
import ru.SMSfinance.library_test_task.store.entities.BookEntity;
import ru.SMSfinance.library_test_task.store.repositories.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Tag(name = "book_methods")
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final MessageSender messageSender;

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
    public AckDto createBook(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "published_date") LocalDate publishedDate) {
        return messageSender.sendCreateBookMessage(title, author, publishedDate);
    }

    @PatchMapping(UPDATE_BOOK)
    public AckDto updateBook(
            @PathVariable Long book_id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "published_date") String publishedDate) {
        return messageSender.sendUpdateBookMessage(book_id, title, author, publishedDate);
    }

    @DeleteMapping(DELETE_BOOK)
    public AckDto deleteBook(@PathVariable Long book_id){
        return messageSender.sendDeleteBookMessage(book_id);
    }
}
