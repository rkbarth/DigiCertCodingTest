package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.Genre;
import com.example.demo.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LibraryControllerTest {

    private LibraryService libraryService;
    private LibraryController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        libraryService = Mockito.mock(LibraryService.class);
        controller = new LibraryController(libraryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listReturnsBooks() throws Exception {
        Book b = new Book(UUID.randomUUID(), "Title", "Author", "ISBN", Instant.now(), 250, "Short synopsis", Genre.TECHNOLOGY, "005.1");
        when(libraryService.list(Optional.empty(), Optional.empty(), Optional.empty(), 0, 10)).thenReturn(List.of(b));

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title"))
                .andExpect(jsonPath("$[0].pages").value(250))
                .andExpect(jsonPath("$[0].genre").value("TECHNOLOGY"));
    }

    @Test
    void getReturnsBook() throws Exception {
        UUID id = UUID.randomUUID();
        Book b = new Book(id, "Title2", "Author2", "ISBN2", Instant.now(), 300, "Another synopsis", Genre.TECHNOLOGY, "005.1");
        when(libraryService.get(eq(id))).thenReturn(Optional.of(b));

        mockMvc.perform(get("/api/v1/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title2"))
                .andExpect(jsonPath("$.pages").value(300))
                .andExpect(jsonPath("$.genre").value("TECHNOLOGY"));
    }

    @Test
    void postCreatesBook() throws Exception {
        Book created = new Book(UUID.fromString("33333333-3333-3333-3333-333333333333"), "DemoBook", "d", "ISBN-1", Instant.now(), 200, "Demo synopsis", Genre.TECHNOLOGY, "005.1");
        when(libraryService.create(any())).thenReturn(created);

        mockMvc.perform(post("/api/v1/books")
                        .contentType("application/json")
                        .content("{\"title\":\"DemoBook\",\"author\":\"d\",\"isbn\":\"ISBN-1\",\"pages\":200,\"synopsis\":\"Demo synopsis\",\"genre\":\"TECHNOLOGY\",\"deweyDecimal\":\"005.1\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/v1/books/33333333-3333-3333-3333-333333333333")));
    }

    @Test
    void putUpdatesBook() throws Exception {
        UUID id = UUID.randomUUID();
        Book updated = new Book(id, "Updated", "Auth", "ISBNU", Instant.now(), 400, "Updated synopsis", Genre.TECHNOLOGY, "005.1");
        when(libraryService.update(eq(id), any())).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/api/v1/books/{id}", id)
                        .contentType("application/json")
                        .content("{\"title\":\"Updated\",\"author\":\"Auth\",\"pages\":400,\"synopsis\":\"Updated synopsis\",\"genre\":\"TECHNOLOGY\",\"deweyDecimal\":\"005.1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"))
                .andExpect(jsonPath("$.synopsis").value("Updated synopsis"))
                .andExpect(jsonPath("$.genre").value("TECHNOLOGY"));
    }

    @Test
    void deleteRemovesBook() throws Exception {
        UUID id = UUID.randomUUID();
        when(libraryService.delete(eq(id))).thenReturn(true);

        mockMvc.perform(delete("/api/v1/books/{id}", id))
                .andExpect(status().isNoContent());
    }
}
