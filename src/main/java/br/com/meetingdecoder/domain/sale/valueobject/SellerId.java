package br.com.meetingdecoder.domain.sale.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

import java.util.UUID;

public class SellerId {
    private final UUID value;

    private SellerId(UUID value) {
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

    public static SellerId of(UUID value) {
        return new SellerId(value);
    }

    public static SellerId of(String value) {
        return new SellerId(UUID.fromString(value));
    }

    public UUID value() {
        return value;
    }
}
