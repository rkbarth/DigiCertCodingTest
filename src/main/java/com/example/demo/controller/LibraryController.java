package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.Genre;
import com.example.demo.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Library")
@RestController
@RequestMapping("/api/v1/books")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Operation(summary = "List books")
    @GetMapping
    public List<Book> list(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) String dewey,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return libraryService.list(Optional.ofNullable(author), Optional.ofNullable(genre), Optional.ofNullable(dewey), page, size);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Operation(summary = "Get book by id")
    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable UUID id) {
        return libraryService.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a book")
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Book book, UriComponentsBuilder ucb) {
        Book created = libraryService.create(book);
        URI location = ucb.path("/api/v1/books/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update a book")
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable UUID id, @Valid @RequestBody Book book) {
        return libraryService.update(id, book).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a book")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return libraryService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
