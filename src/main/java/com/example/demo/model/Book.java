package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Schema(description = "A book in the library")
public class Book {

    @Schema(description = "Unique identifier", example = "33333333-3333-3333-3333-333333333333")
    private UUID id;

    @NotBlank
    @Schema(description = "Title of the book", example = "Effective Java")
    private String title;

    @Schema(description = "Author name", example = "Joshua Bloch")
    private String author;

    @Schema(description = "ISBN identifier", example = "978-0134685991")
    private String isbn;

    @Schema(description = "Publication date/time (UTC)", example = "2018-01-06T00:00:00Z")
    private Instant publishedAt;

    @Schema(description = "Number of pages", example = "320")
    private Integer pages;

    @Schema(description = "Short synopsis", example = "A classic book about practices and patterns.")
    private String synopsis;

    @Schema(description = "Genre of the book", example = "TECHNOLOGY")
    private Genre genre;

    @Schema(description = "Dewey Decimal classification", example = "005.133")
    private String deweyDecimal;

    public Book() {}

    public Book(UUID id, String title, String author, String isbn, Instant publishedAt, Integer pages, String synopsis, Genre genre, String deweyDecimal) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedAt = publishedAt;
        this.pages = pages;
        this.synopsis = synopsis;
        this.genre = genre;
        this.deweyDecimal = deweyDecimal;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }

    public Integer getPages() { return pages; }
    public void setPages(Integer pages) { this.pages = pages; }

    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public String getDeweyDecimal() { return deweyDecimal; }
    public void setDeweyDecimal(String deweyDecimal) { this.deweyDecimal = deweyDecimal; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
