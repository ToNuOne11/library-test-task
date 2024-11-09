package ru.SMSfinance.library_test_task.api.factories;

import org.springframework.stereotype.Component;
import ru.SMSfinance.library_test_task.api.dto.BookDto;
import ru.SMSfinance.library_test_task.store.entities.BookEntity;

@Component
public class BookDtoFactory {
    public BookDto makeBookDto(BookEntity entity){
        return BookDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .publishedDate(entity.getPublishedDate())
                .build();
    }
}
