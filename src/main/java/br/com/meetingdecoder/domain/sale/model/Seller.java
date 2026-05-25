package br.com.meetingdecoder.domain.sale.model;

import br.com.meetingdecoder.domain.sale.enums.SellerType;
import br.com.meetingdecoder.domain.sale.valueobject.Email;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

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
        DomainValidation.notNull(id, "id");
        DomainValidation.notNull(type, "type");
        DomainValidation.notBlank(name, "name");
        DomainValidation.notBlank(email.value(), "email");
        DomainValidation.notNull(createdAt, "createdAt");
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

    public UUID getId() {
        return id.value();
    }

    public UUID getManagerId() {
        return managerId.value();
    }

    public SellerType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email.value();
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}