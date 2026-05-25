package br.com.meetingdecoder.domain.sale.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

import java.util.UUID;

public class SellerId {
    private final UUID value;

    private SellerId(UUID value) {
        validate(value);
        this.value = value;
    }

    private void validate(UUID value) {
        DomainValidation.notNull(value, "id");
    }

    public SellerId of(UUID value) {
        return new SellerId(value);
    }

    public SellerId of(String value) {
        return new SellerId(UUID.fromString(value));
    }

    public UUID value() {
        return value;
    }
}
