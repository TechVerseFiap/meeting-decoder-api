package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.enums.ServiceCategory;
import br.com.meetingdecoder.domain.validation.DomainValidation;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private final UUID id;
    private String name;
    private ServiceCategory category;
    private String description;
    private BigDecimal price;

    private Product(
            String name,
            ServiceCategory category,
            String description,
            BigDecimal price
    ) {
        validate(name, category, description, price);

        this.id = UUID.randomUUID();
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    private void validate(
            String name,
            ServiceCategory category,
            String description,
            BigDecimal price
    ) {
        DomainValidation.notBlank(name, "name");
        DomainValidation.notNull(category, "category");
        DomainValidation.notBlank(description, "description");
        DomainValidation.positive(price, "price");
    }

    public static Product create(
            String name,
            ServiceCategory category,
            String description,
            BigDecimal price
    ) {
        return new Product(name, category, description, price);
    }

    public void update(
            String name,
            ServiceCategory category,
            String description,
            BigDecimal price
    ) {
        if (name != null) {
            DomainValidation.notBlank(name, "name");
            this.name = name;
        }
        if (category != null) {
            this.category = category;
        }
        if (description != null) {
            DomainValidation.notBlank(description, "description");
            this.description = description;
        }
        if (price != null) {
            DomainValidation.positive(price, "price");
            this.price = price;
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ServiceCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
