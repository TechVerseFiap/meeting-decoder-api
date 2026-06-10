package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

import java.util.UUID;

public final class TranscriptionId {
    private final UUID value;

    private TranscriptionId(UUID value) {
        ErrorCollector.builder()
                .requireNotNull(
                        value,
                        "id",
                        DomainErrorCode.EMPTY_FIELD
                )
                .validate();

        this.value = value;
    }

    public static TranscriptionId of(UUID value) {
        return new TranscriptionId(value);
    }

    public static TranscriptionId of(String value) {
        return new TranscriptionId(UUID.fromString(value));
    }

    public UUID value() {
        return value;
    }
}
