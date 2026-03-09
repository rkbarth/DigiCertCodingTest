package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryServiceTest {

    private LibraryService service;

    @BeforeEach
    void setUp() {
        service = new LibraryService();
        service.clear();

        // seed some books
        service.create(new Book(UUID.randomUUID(), "Java 101", "Jane Doe", "ISBN-1", Instant.now(), 200, "Intro to Java", Genre.NON_FICTION, "005.133"));
        service.create(new Book(UUID.randomUUID(), "Spring Guide", "John Smith", "ISBN-2", Instant.now(), 320, "Spring boot", Genre.NON_FICTION, "005.133"));
        service.create(new Book(UUID.randomUUID(), "Fictional Tale", "A Writer", "ISBN-3", Instant.now(), 180, "A story", Genre.FICTION, "813.6"));

        // add more for pagination
        for (int i = 0; i < 12; i++) {
            service.create(new Book(UUID.randomUUID(), "Book " + i, "Author " + i, "ISBN-B" + i, Instant.now(), 100 + i, "syn", Genre.NON_FICTION, "005.1"));
        }
    }

    @Test
    void filterByAuthor() {
        List<Book> results = service.list(Optional.of("Jane"), Optional.empty(), Optional.empty(), 0, 10);
        assertEquals(1, results.size());
        assertEquals("Jane Doe", results.get(0).getAuthor());
    }

    @Test
    void filterByGenre() {
        List<Book> results = service.list(Optional.empty(), Optional.ofNullable(Genre.FICTION), Optional.empty(), 0, 10);
        assertEquals(1, results.size());
        assertEquals(Genre.FICTION, results.get(0).getGenre());
    }

    @Test
    void filterByDeweyPrefix() {
        List<Book> results = service.list(Optional.empty(), Optional.empty(), Optional.of("005"), 0, 100);
        assertTrue(results.size() >= 2); // many non-fiction books use 005.
    }

    @Test
    void paginationWorks() {
        // we seeded 12 + 3 = 15 books total; request page 1 size 5 => items 5..9 => 5 items
        List<Book> page1 = service.list(Optional.empty(), Optional.empty(), Optional.empty(), 1, 5);
        assertEquals(5, page1.size());
    }
}
