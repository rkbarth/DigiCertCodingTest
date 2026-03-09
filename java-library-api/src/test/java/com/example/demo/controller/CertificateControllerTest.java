package com.example.demo.controller;

import com.example.demo.model.Certificate;
import com.example.demo.service.CertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CertificateControllerTest {

    private MockMvc mockMvc;

    private CertificateService service;

    private Certificate sample;

    @BeforeEach
    void setUp() {
        service = mock(CertificateService.class);
        CertificateController controller = new CertificateController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        sample = new Certificate(UUID.randomUUID(), "Alice", "SN123", Instant.parse("2020-01-01T00:00:00Z"));
    }

    @Test
    void list_returnsAll() throws Exception {
        when(service.list()).thenReturn(List.of(sample));
        mockMvc.perform(get("/api/v1/certificates"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].owner").value("Alice"));
    }

    @Test
    void get_found() throws Exception {
        when(service.get(sample.getId())).thenReturn(Optional.of(sample));
        mockMvc.perform(get("/api/v1/certificates/" + sample.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serialNumber").value("SN123"));
    }

    @Test
    void get_notFound() throws Exception {
        when(service.get(any())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/certificates/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_returnsCreated() throws Exception {
        Certificate created = new Certificate(UUID.randomUUID(), "Bob", "SN456", Instant.now());
        when(service.create(any())).thenReturn(created);
        String body = "{\"owner\":\"Bob\",\"serialNumber\":\"SN456\"}";
        mockMvc.perform(post("/api/v1/certificates").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/certificates/" + created.getId()))
                .andExpect(jsonPath("$.owner").value("Bob"));
    }

    @Test
    void update_found() throws Exception {
        Certificate updated = new Certificate(sample.getId(), "Carol", "SN789", Instant.now());
        when(service.update(eq(sample.getId()), any())).thenReturn(Optional.of(updated));
        String body = "{\"owner\":\"Carol\",\"serialNumber\":\"SN789\"}";
        mockMvc.perform(put("/api/v1/certificates/" + sample.getId()).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value("Carol"));
    }

    @Test
    void update_notFound() throws Exception {
        when(service.update(any(), any())).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/v1/certificates/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_found() throws Exception {
        when(service.delete(sample.getId())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/certificates/" + sample.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_notFound() throws Exception {
        when(service.delete(any())).thenReturn(false);
        mockMvc.perform(delete("/api/v1/certificates/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
