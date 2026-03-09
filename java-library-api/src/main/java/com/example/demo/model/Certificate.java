package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Schema(description = "Represents a certificate issued to an owner")
public class Certificate {
    @Schema(description = "Unique identifier for the certificate", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Owner of the certificate", example = "Alice")
    private String owner;

    @Schema(description = "Serial number assigned to the certificate", example = "SN123456")
    private String serialNumber;

    @Schema(description = "Timestamp when the certificate was issued", example = "2020-01-01T00:00:00Z")
    private Instant issuedAt;

    public Certificate() {
    }

    public Certificate(UUID id, String owner, String serialNumber, Instant issuedAt) {
        this.id = id;
        this.owner = owner;
        this.serialNumber = serialNumber;
        this.issuedAt = issuedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(serialNumber, that.serialNumber) &&
                Objects.equals(issuedAt, that.issuedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, serialNumber, issuedAt);
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", issuedAt=" + issuedAt +
                '}';
    }
}
