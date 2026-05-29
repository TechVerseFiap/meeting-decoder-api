package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

import java.util.UUID;

public class MeetingId {
    private final UUID value;

    private MeetingId(UUID value) {
        validate(value);
        this.value = value;
    }

    private void validate(UUID value) {
        DomainValidation.notNull(value, "id");
    }

    public MeetingId of(UUID value) {
        return new MeetingId(value);
    }

    public MeetingId of(String value) {
        return new MeetingId(UUID.fromString(value));
    }

    public UUID value() {
        return value;
    }
}
