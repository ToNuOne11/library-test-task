package ru.SMSfinance.library_test_task.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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


    @GetMapping(GET_ALL_BOOKS)
    public List<BookDto> getAllBooks(){
        return bookService.findAllBooks();
    }


}
