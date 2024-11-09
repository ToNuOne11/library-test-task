package ru.SMSfinance.library_test_task.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.SMSfinance.library_test_task.store.entities.BookEntity;

import java.awt.print.Book;
import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    BookEntity findByTitle(String title);
}
