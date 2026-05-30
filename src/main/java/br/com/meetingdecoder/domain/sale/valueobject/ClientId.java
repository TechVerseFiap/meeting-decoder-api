package br.com.meetingdecoder.domain.sale.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

import java.util.UUID;

public class ClientId {
    private final UUID value;

    private ClientId(UUID value) {
        validate(value);
        this.value = value;
    }

    private void validate(UUID value) {
        DomainValidation.notNull(value, "id");
    }

    public ClientId of(UUID value) {
        return new ClientId(value);
    }

    public ClientId of(String value) {
        return new ClientId(UUID.fromString(value));
    }

    public UUID value() {
        return value;
    }
}
