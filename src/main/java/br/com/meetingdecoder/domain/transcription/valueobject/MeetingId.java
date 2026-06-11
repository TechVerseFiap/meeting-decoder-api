package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

import java.util.UUID;

public final class MeetingId {
    private final UUID value;

    private MeetingId(UUID value) {
        ErrorCollector.builder()
                .requireNotNull(
                        value,
                        "id",
                        DomainErrorCode.EMPTY_FIELD
                )
                .validate();

        this.value = value;
    }

    public static MeetingId of(UUID value) {
        return new MeetingId(value);
    }

    public static MeetingId of(String value) {
        return new MeetingId(UUID.fromString(value));
    }

    public UUID value() {
        return value;
    }
}
