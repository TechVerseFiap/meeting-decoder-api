package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

import java.util.UUID;

public class Transcription {
    private UUID id;
    private String transcriptionContent;
    private UUID meetingId;

    private Transcription(
            String transcriptionContent,
            UUID meetingId
    ) {
        validate(transcriptionContent, meetingId);

        this.id = UUID.randomUUID();
        this.transcriptionContent = transcriptionContent;
        this.meetingId = meetingId;
    }

    private void validate(
            String transcriptionContent,
            UUID meetingId
    ) {
        DomainValidation.notBlank(transcriptionContent, "transcriptionContent");
        DomainValidation.notNull(meetingId, "meetingId");
    }

    public static Transcription create(
            String transcriptionContent,
            UUID meetingId
    ) {
        return new Transcription(transcriptionContent, meetingId);
    }
}
