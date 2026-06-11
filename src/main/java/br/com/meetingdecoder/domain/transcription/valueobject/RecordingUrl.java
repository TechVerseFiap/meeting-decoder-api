package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

public class RecordingUrl {
    private final String value;

    private RecordingUrl(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        ErrorCollector.builder()
                .requireNotBlank(
                        value,
                        "recordingUrl",
                        DomainErrorCode.EMPTY_FIELD
                )
                .validate();
    }

    public static RecordingUrl of(String value) {
        return new RecordingUrl(value);
    }

    public String value() {
        return value;
    }
}
