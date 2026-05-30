package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

public class RecordingUrl {
    private final String value;

    private RecordingUrl(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        DomainValidation.notBlank(value, "recordingurl");
    }

    public static RecordingUrl of(String value) {
        return new RecordingUrl(value);
    }

    public String value() {
        return value;
    }
}
