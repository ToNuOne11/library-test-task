package ru.SMSfinance.library_test_task.api.services;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.SMSfinance.library_test_task.api.dto.AckDto;
import ru.SMSfinance.library_test_task.api.dto.BookDto;
import ru.SMSfinance.library_test_task.api.factories.BookDtoFactory;
import ru.SMSfinance.library_test_task.store.repositories.BookRepository;

import java.time.LocalDate;
import java.util.Optional;

@Setter
@Service
public class MessageSender {
    @Value("${queue.book.name}")
    private String queueName;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public AckDto sendCreateBookMessage(String title, String author, LocalDate publishedDate) {
        amqpTemplate.convertAndSend(queueName, "CREATE:" + title + ":" + author + ":" + publishedDate);
        return AckDto.makeDefault(true);
    }

    public AckDto sendUpdateBookMessage(Long book_id, String title, String author, String publishedDate) {
        amqpTemplate.convertAndSend(queueName, "UPDATE:" + book_id + ":" + title + ":" + author + ":" + publishedDate);
        return AckDto.makeDefault(true);
    }

    public AckDto sendDeleteBookMessage(Long book_id) {
        amqpTemplate.convertAndSend(queueName, "DELETE:" + book_id);
        return AckDto.makeDefault(true);
    }
}
