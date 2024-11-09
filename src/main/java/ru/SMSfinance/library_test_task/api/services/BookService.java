package ru.SMSfinance.library_test_task.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SMSfinance.library_test_task.api.dto.BookDto;
import ru.SMSfinance.library_test_task.api.exeptions.NotFoundException;
import ru.SMSfinance.library_test_task.api.factories.BookDtoFactory;
import ru.SMSfinance.library_test_task.store.repositories.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookDtoFactory bookDtoFactory;

    public List<BookDto> findAllBooks() {
        List<BookDto> books = bookRepository.findAll()
                .stream()
                .map(bookDtoFactory::makeBookDto)
                .toList();
        if (books.isEmpty()) {
            throw new NotFoundException("Books not found");
        }
        return books;
    }

    public BookDto findBookById(Long id) {
        return bookDtoFactory.makeBookDto(bookRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Book not found")
                )
        );
    }
}
