package ru.SMSfinance.library_test_task.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SMSfinance.library_test_task.api.dto.AckDto;
import ru.SMSfinance.library_test_task.api.dto.BookDto;
import ru.SMSfinance.library_test_task.api.exeptions.BadRequestException;
import ru.SMSfinance.library_test_task.api.exeptions.NotFoundException;
import ru.SMSfinance.library_test_task.api.factories.BookDtoFactory;
import ru.SMSfinance.library_test_task.store.entities.BookEntity;
import ru.SMSfinance.library_test_task.store.repositories.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookDtoFactory bookDtoFactory;
    @Transactional
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
    @Transactional
    public BookDto findBookById(Long id) {
        return bookDtoFactory.makeBookDto(getBookOrThrowException(id));
    }

    @Transactional
    public BookDto createBook(String title, String author, LocalDate publishedDate) {
        if (title.isBlank()) {
            throw new BadRequestException("Title is empty");
        }

        if (author.isBlank()) {
            throw new BadRequestException("Author is empty");
        }

        if (Objects.nonNull(bookRepository.findByTitleAndAuthor(title, author))) {
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

    @Transactional
    public BookDto updateBook(Long id, String title, String author, String publishedDate) {

        BookEntity book = getBookOrThrowException(id);

        if (!title.isBlank()) {
            book.setTitle(title);
        }
        if (!author.isBlank()) {
            book.setAuthor(title);
        }
        if (!publishedDate.isBlank()) {
            try {
                book.setPublishedDate(LocalDate.parse(publishedDate));
            }
            catch (BadRequestException e) {
                throw new BadRequestException("Date is incorrect");
            }

        }

        Optional<BookEntity> optionalBookEntity = Optional.ofNullable(bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor()));
        if (optionalBookEntity.isPresent() && !Objects.equals(id, optionalBookEntity.get().getId())) {
            throw new BadRequestException("Book already exists.");
        }

        book = bookRepository.saveAndFlush(book);

        return bookDtoFactory.makeBookDto(book);
    }

    @Transactional
    public AckDto deleteBook(Long id) {
        BookEntity book = getBookOrThrowException(id);

        bookRepository.delete(book);

        return AckDto.makeDefault(true);
    }

    private BookEntity getBookOrThrowException(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Book not found")
                );
    }
}
