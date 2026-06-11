package br.com.meetingdecoder.domain.sale.model;

import br.com.meetingdecoder.domain.sale.enums.SellerType;
import br.com.meetingdecoder.domain.sale.valueobject.Email;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

import java.time.LocalDateTime;
import java.util.UUID;

public class Seller {
    private final SellerId id;
    private SellerId managerId;
    private SellerType type;
    private String name;
    private Email email;
    private boolean active;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Seller(
            SellerId id,
            SellerId managerId,
            SellerType type,
            String name,
            Email email,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        validate(id, type, name, email, createdAt);
        this.id = id;
        this.managerId = managerId;
        this.type = type;
        this.name = name;
        this.email = email;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validate(
            SellerId id,
            SellerType type,
            String name,
            Email email,
            LocalDateTime createdAt
    ) {
        ErrorCollector.builder()
                .requireNotNull(id, "id", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(type, "type", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(name, "name", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(email, "email", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(createdAt, "createdAt", DomainErrorCode.EMPTY_FIELD)
                .validate();
    }

    public static Seller create(
            SellerId id,
            SellerId managerId,
            SellerType type,
            String name,
            Email email
    ) {
        return new Seller(
                id,
                managerId,
                type,
                name,
                email,
                true,
                LocalDateTime.now(),
                null
        );
    }

    public Seller update(
            SellerId managerId,
            SellerType type,
            String name,
            Email email
    ) {
        if (managerId != null) {
            this.managerId = managerId;
        }
        if (type != null) {
            this.type = type;
        }
        if (name != null) {
            DomainValidation.notBlank(name, "name");
            this.name = name;
        }
        if (email != null) {
            DomainValidation.notBlank(email.value(), "email");
            this.email = email;
        }
        return this;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public UUID id() {
        return id.value();
    }

    public UUID managerId() {
        return managerId != null ? managerId.value() : null;
    }

    public SellerType type() {
        return type;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email.value();
    }

    public boolean active() {
        return active;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}