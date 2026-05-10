package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.enums.ServiceCategory;
import br.com.meetingdecoder.domain.validation.DomainValidation;

import java.math.BigDecimal;
import java.util.UUID;

public class ServiceRecommendation {
    private UUID id;
    private String name;
    private ServiceCategory category;
    private String description;
    private BigDecimal price;

    private ServiceRecommendation(
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

    public static ServiceRecommendation create(
            String name,
            ServiceCategory category,
            String description,
            BigDecimal price
    ) {
        return new ServiceRecommendation(name, category, description, price);
    }
}
