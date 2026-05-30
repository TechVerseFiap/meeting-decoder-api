package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

public class TranscriptJson {
    private final String value;

    private TranscriptJson(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        DomainValidation.notBlank(value, "transcriptjson");
        if (!value.trim().startsWith("{")) {
            throw new IllegalArgumentException("The JSON transcript must contain {} at the beginning.");
        }
    }

    public static TranscriptJson of(String value) {
        return new TranscriptJson(value);
    }

    public String value() {
        return value;
    }
}
