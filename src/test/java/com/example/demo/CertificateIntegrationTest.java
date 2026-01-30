package com.example.demo;

import com.example.demo.model.Certificate;
import com.example.demo.service.CertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CertificateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CertificateService service;

    @BeforeEach
    void setUp() {
        service.clear();
    }

    @Test
    void create_then_get_returnsCreatedAndFound() throws Exception {
        String body = "{\"owner\":\"Eve\",\"serialNumber\":\"SN999\"}";
        var createResult = mockMvc.perform(post("/api/v1/certificates").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        String location = createResult.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        // get created resource
        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner").value("Eve"))
                .andExpect(jsonPath("$.serialNumber").value("SN999"));
    }

    @Test
    void delete_removesResource() throws Exception {
        // create
        Certificate created = service.create(new Certificate(null, "Del", "SNDEL", null));
        UUID id = created.getId();

        mockMvc.perform(delete("/api/v1/certificates/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/certificates/" + id))
                .andExpect(status().isNotFound());
    }
}
