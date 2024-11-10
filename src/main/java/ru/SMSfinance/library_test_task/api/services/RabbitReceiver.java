package ru.SMSfinance.library_test_task.api.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.SMSfinance.library_test_task.api.dto.AckDto;
import ru.SMSfinance.library_test_task.api.exeptions.NotFoundException;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitReceiver {
    private final BookService bookService;

    @RabbitListener(queues = {"queueBook"})
    public void bookReceiver(String message) {
        try {
            log.info(message);
            if (message.startsWith("CREATE:")) {
                String [] strings = message.split(":");
                bookService.createBook(strings[1], strings[2], LocalDate.parse(strings[3]));
            } else if (message.startsWith("UPDATE:")) {
                String [] strings = message.split(":");
                bookService.updateBook(Long.valueOf(strings[1]), strings[2], strings[3], strings[4]);
            } else if (message.startsWith("DELETE:")) {
                bookService.deleteBook(Long.valueOf(message.split(":")[1]));
            }
        } catch (Exception e) {
            log.error("error: " + e.getMessage() + " | message: " + message);
        }
    }
}
