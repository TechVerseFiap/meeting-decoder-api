package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

public class TranscriptJson {
    private final String value;

    private TranscriptJson(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        ErrorCollector.builder()
                .requireNotBlank(
                        value,
                        "transcriptJson",
                        DomainErrorCode.EMPTY_FIELD
                )
                .check(
                        value == null
                                || value.trim().startsWith("{"),
                        DomainError.of(
                                DomainErrorCode.INVALID_FIELD,
                                "transcriptJson",
                                "Transcript must start with '{'"
                        )
                )
                .validate();
    }

    public static TranscriptJson of(String value) {
        return new TranscriptJson(value);
    }

    public String value() {
        return value;
    }
}
