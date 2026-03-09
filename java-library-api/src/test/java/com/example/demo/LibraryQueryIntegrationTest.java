package com.example.demo;

import com.example.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryQueryIntegrationTest {

    @LocalServerPort
    int port;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    void queryByAuthorGenreDeweyWithPagination() {
        String url = "http://localhost:" + port + "/api/v1/books?author=Jane&genre=NON_FICTION&dewey=005&page=0&size=5";

        // create a matching book first to ensure predictable results
        String postUrl = "http://localhost:" + port + "/api/v1/books";
        String payload = "{\"title\":\"Integration Jane\",\"author\":\"Jane Doe\",\"isbn\":\"ISBN-J\",\"pages\":120,\"synopsis\":\"test\",\"genre\":\"NON_FICTION\",\"deweyDecimal\":\"005.133\"}";
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Content-Type", "application/json");
        org.springframework.http.HttpEntity<String> req = new org.springframework.http.HttpEntity<>(payload, headers);
        ResponseEntity<Void> postResp = restTemplate.postForEntity(postUrl, req, Void.class);
        assertThat(postResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<Book[]> resp = restTemplate.getForEntity(url, Book[].class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book[] books = resp.getBody();
        assertThat(books).isNotNull();
        assertThat(books.length).isGreaterThan(0);
        assertThat(Arrays.stream(books).anyMatch(b -> "Jane Doe".equals(b.getAuthor()))).isTrue();
    }
}
