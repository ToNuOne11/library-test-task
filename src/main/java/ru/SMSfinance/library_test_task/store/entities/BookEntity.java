package ru.SMSfinance.library_test_task.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title", columnDefinition = "text", nullable = false)
    private String title;

    @Column(name = "author", columnDefinition = "text", nullable = false)
    private String author;

    @Column(name = "publishedDate", nullable = false)
    private LocalDate publishedDate;
}
