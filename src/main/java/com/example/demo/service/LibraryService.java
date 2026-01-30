package com.example.demo.service;

import com.example.demo.model.Book;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.example.demo.model.Genre;

@Service
public class LibraryService {

    private final ConcurrentMap<UUID, Book> store = new ConcurrentHashMap<>();

    public List<Book> list() {
        return list(Optional.empty(), Optional.empty(), Optional.empty(), 0, Integer.MAX_VALUE);
    }

    public List<Book> list(Optional<String> author, Optional<Genre> genre, Optional<String> dewey, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("page must be >= 0 and size must be > 0");
        }

        return store.values().stream()
                .filter(b -> author.map(a -> b.getAuthor() != null && b.getAuthor().toLowerCase().contains(a.toLowerCase())).orElse(true))
                .filter(b -> genre.map(g -> b.getGenre() == g).orElse(true))
                .filter(b -> dewey.map(d -> b.getDeweyDecimal() != null && b.getDeweyDecimal().startsWith(d)).orElse(true))
                .sorted(Comparator.comparing(Book::getTitle, Comparator.nullsLast(String::compareToIgnoreCase)))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public Optional<Book> get(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public Book create(Book book) {
        UUID id = book.getId() == null ? UUID.randomUUID() : book.getId();
        if (book.getPublishedAt() == null) book.setPublishedAt(Instant.now());
        book.setId(id);
        store.put(id, book);
        return book;
    }

    public Optional<Book> update(UUID id, Book book) {
        return Optional.ofNullable(store.computeIfPresent(id, (k, existing) -> {
            existing.setTitle(book.getTitle());
            existing.setAuthor(book.getAuthor());
            existing.setIsbn(book.getIsbn());
            return existing;
        }));
    }

    public boolean delete(UUID id) {
        return store.remove(id) != null;
    }

    public void clear() { store.clear(); }
}
