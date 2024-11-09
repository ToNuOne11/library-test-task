package ru.SMSfinance.library_test_task.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.SMSfinance.library_test_task.api.dto.BookDto;
import ru.SMSfinance.library_test_task.api.factories.BookDtoFactory;
import ru.SMSfinance.library_test_task.api.services.BookService;
import ru.SMSfinance.library_test_task.store.entities.BookEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    public static final String GET_ALL_BOOKS = "/api/books";
    public static final String GET_BOOK_BY_ID = "/api/books/{book_id}";


    @GetMapping(GET_ALL_BOOKS)
    public List<BookDto> getAllBooks(){
        return bookService.findAllBooks();
    }
    @GetMapping(GET_BOOK_BY_ID)
    public BookDto getBookById(@PathVariable Long book_id){
        return bookService.findBookById(book_id);
    }



}
