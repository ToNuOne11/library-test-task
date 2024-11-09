package ru.SMSfinance.library_test_task.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SMSfinance.library_test_task.api.dto.BookDto;
import ru.SMSfinance.library_test_task.api.exeptions.BadRequestException;
import ru.SMSfinance.library_test_task.api.exeptions.NotFoundException;
import ru.SMSfinance.library_test_task.api.factories.BookDtoFactory;
import ru.SMSfinance.library_test_task.store.entities.BookEntity;
import ru.SMSfinance.library_test_task.store.repositories.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    public BookDto createBook(String title, String author, LocalDate publishedDate) {
        if (title.isBlank()) {
            throw new BadRequestException("Title is empty");
        }
        if(Objects.nonNull(bookRepository.findByTitleAndAuthor(title, author))){
            throw new BadRequestException("Book already exists.");
        }
        final BookEntity book = bookRepository.saveAndFlush(
                BookEntity.builder()
                        .title(title)
                        .author(author)
                        .publishedDate(publishedDate)
                        .build()
        );

        return bookDtoFactory.makeBookDto(book);
    }
}
