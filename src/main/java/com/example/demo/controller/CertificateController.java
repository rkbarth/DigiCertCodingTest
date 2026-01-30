package com.example.demo.controller;

import com.example.demo.model.Certificate;
import com.example.demo.service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Certificates", description = "Operations on certificates")
@RestController
@RequestMapping("/api/v1/certificates")
public class CertificateController {
    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @Operation(summary = "List certificates", description = "Returns all certificates")
    @GetMapping
    public List<Certificate> list() {
        return service.list();
    }

    @Operation(summary = "Get certificate", description = "Get a certificate by its UUID")
    @GetMapping("/{id}")
    public ResponseEntity<Certificate> get(@Parameter(description = "UUID of the certificate", required = true) @PathVariable UUID id) {
        return service.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create certificate", description = "Create a new certificate")
    @PostMapping
    public ResponseEntity<Certificate> create(@RequestBody Certificate certificate) {
        Certificate created = service.create(certificate);
        return ResponseEntity.created(URI.create("/api/v1/certificates/" + created.getId())).body(created);
    }

    @Operation(summary = "Update certificate", description = "Update an existing certificate by UUID")
    @PutMapping("/{id}")
    public ResponseEntity<Certificate> update(@Parameter(description = "UUID of the certificate", required = true) @PathVariable UUID id, @RequestBody Certificate certificate) {
        return service.update(id, certificate).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete certificate", description = "Delete a certificate by UUID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "UUID of the certificate", required = true) @PathVariable UUID id) {
        boolean removed = service.delete(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
