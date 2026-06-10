package br.com.meetingdecoder.domain.sale.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

import java.util.UUID;

public class ClientId {
    private final UUID value;

    private ClientId(UUID value) {
        validate(value);
        this.value = value;
    }

    private void validate(UUID value) {
        ErrorCollector.builder()
                .requireNotNull(
                        value,
                        "id",
                        DomainErrorCode.EMPTY_FIELD
                )
                .validate();
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
