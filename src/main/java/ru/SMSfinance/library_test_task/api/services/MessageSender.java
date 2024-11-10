package ru.SMSfinance.library_test_task.api.services;

import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Setter
@Service
public class MessageSender {
    @Value("${queue.book.name}")
    private String queueName;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendCreateBookMessage(String title, String author, LocalDate publishedDate) {
        amqpTemplate.convertAndSend(queueName, "CREATE:" + title + ":" + author + ":" + publishedDate);
    }

    public void sendUpdateBookMessage(String title, String author, LocalDate publishedDate) {
        amqpTemplate.convertAndSend(queueName, "UPDATE:");
    }

    public void sendDeleteBookMessage(Long book_id) {
        amqpTemplate.convertAndSend(queueName, "DELETE:" + book_id);
    }
}
