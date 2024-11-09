package ru.SMSfinance.library_test_task.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

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
    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "author", columnDefinition = "text")
    private String author;

    @Builder.Default
    private Instant publishedDate = Instant.now();

}
