package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

import java.util.UUID;

public class TranscriptionId {
    private final UUID value;

    private TranscriptionId(UUID value) {
        this.value = value;
    }

    private void validate(UUID value) {
        DomainValidation.notNull(value, "id");
    }

    public TranscriptionId of(UUID value) {
        return new TranscriptionId(value);
    }

    public TranscriptionId of(String value) {
        return new TranscriptionId(UUID.fromString(value));
    }

    public UUID value() {
        return value;
    }
}
