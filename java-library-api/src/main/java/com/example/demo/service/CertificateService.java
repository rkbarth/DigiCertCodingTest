package com.example.demo.service;

import com.example.demo.model.Certificate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CertificateService {
    private final Map<UUID, Certificate> store = new ConcurrentHashMap<>();

    public List<Certificate> list() {
        return new ArrayList<>(store.values());
    }

    public Optional<Certificate> get(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public Certificate create(Certificate c) {
        UUID id = c.getId() != null ? c.getId() : UUID.randomUUID();
        Instant issued = c.getIssuedAt() != null ? c.getIssuedAt() : Instant.now();
        Certificate cert = new Certificate(id, c.getOwner(), c.getSerialNumber(), issued);
        store.put(id, cert);
        return cert;
    }

    public Optional<Certificate> update(UUID id, Certificate c) {
        return Optional.ofNullable(store.computeIfPresent(id, (k, existing) -> {
            existing.setOwner(c.getOwner());
            existing.setSerialNumber(c.getSerialNumber());
            existing.setIssuedAt(c.getIssuedAt() != null ? c.getIssuedAt() : existing.getIssuedAt());
            return existing;
        }));
    }

    public boolean delete(UUID id) {
        return store.remove(id) != null;
    }

    // helper for tests
    public void clear() {
        store.clear();
    }
}
