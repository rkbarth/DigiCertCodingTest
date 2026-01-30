package com.example.demo.config;

import com.example.demo.model.Book;
import com.example.demo.service.LibraryService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class LibraryDataLoader implements ApplicationRunner {

    private final LibraryService libraryService;

    public LibraryDataLoader(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Seed demo books for quick manual checks (11 total)
        List<Book> books = List.of(
                new Book(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Demo Book", "Seed Author", "ISBN-DEMO", Instant.now(), 123, "A seeded demo book", com.example.demo.model.Genre.TECHNOLOGY, "000.0"),
                new Book(UUID.fromString("33333334-3333-3333-3333-333333333334"), "Java Basics", "Jane Doe", "978-0000000001", Instant.now(), 200, "Intro to Java", com.example.demo.model.Genre.TECHNOLOGY, "005.133"),
                new Book(UUID.fromString("33333335-3333-3333-3333-333333333335"), "Spring Boot in Action", "John Smith", "978-0000000002", Instant.now(), 320, "Getting started with Spring Boot", com.example.demo.model.Genre.TECHNOLOGY, "005.133"),
                new Book(UUID.fromString("33333336-3333-3333-3333-333333333336"), "Effective Java", "Joshua Bloch", "978-0134685991", Instant.now(), 416, "Best practices for Java", com.example.demo.model.Genre.TECHNOLOGY, "005.133"),
                new Book(UUID.fromString("33333337-3333-3333-3333-333333333337"), "Clean Code", "Robert C. Martin", "978-0132350884", Instant.now(), 464, "Writing clean and maintainable code", com.example.demo.model.Genre.TECHNOLOGY, "005.1"),
                new Book(UUID.fromString("33333338-3333-3333-3333-333333333338"), "Design Patterns", "Erich Gamma", "978-0201633610", Instant.now(), 395, "Classic design patterns", com.example.demo.model.Genre.TECHNOLOGY, "005.12"),
                new Book(UUID.fromString("33333339-3333-3333-3333-333333333339"), "Refactoring", "Martin Fowler", "978-0201485677", Instant.now(), 448, "Improving the design of existing code", com.example.demo.model.Genre.TECHNOLOGY, "005.1"),
                new Book(UUID.fromString("3333333a-3333-3333-3333-33333333333a"), "Concurrency in Practice", "Brian Goetz", "978-0321349606", Instant.now(), 384, "Concurrency concepts and patterns", com.example.demo.model.Genre.TECHNOLOGY, "005.1"),
                new Book(UUID.fromString("3333333b-3333-3333-3333-33333333333b"), "Test-Driven Development", "Kent Beck", "978-0321146533", Instant.now(), 220, "TDD practices and techniques", com.example.demo.model.Genre.TECHNOLOGY, "005.13"),
                new Book(UUID.fromString("3333333c-3333-3333-3333-33333333333c"), "The Pragmatic Programmer", "Andrew Hunt", "978-0201616224", Instant.now(), 352, "Practical programming advice", com.example.demo.model.Genre.TECHNOLOGY, "005.1"),
                new Book(UUID.fromString("3333333d-3333-3333-3333-33333333333d"), "Microservices Patterns", "Chris Richardson", "978-1111111111", Instant.now(), 280, "Patterns for microservices architectures", com.example.demo.model.Genre.TECHNOLOGY, "005.1")
        );
        books.forEach(libraryService::create);
    }
}
