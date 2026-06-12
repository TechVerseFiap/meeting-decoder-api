package br.com.meetingdecoder.domain.transcription.model;

import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptJson;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionConfidence;
import br.com.meetingdecoder.domain.transcription.valueobject.TranscriptionId;

import java.time.LocalDateTime;

public class Transcription {
    private final TranscriptionId id;
    private MeetingId meetingId;
    private String rawText;
    private String cleanText;
    private TranscriptJson formattedText;
    private TranscriptionConfidence modelConfidence;
    private LocalDateTime processedAt;
    private LocalDateTime finishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Transcription(
            TranscriptionId id,
            MeetingId meetingId,
            String rawText,
            String cleanText,
            TranscriptJson formattedText,
            TranscriptionConfidence modelConfidence,
            LocalDateTime processedAt,
            LocalDateTime finishedAt
    ) {
        validate(
                id,
                meetingId,
                rawText,
                cleanText,
                formattedText,
                modelConfidence,
                processedAt,
                finishedAt
        );
        this.id = id;
        this.meetingId = meetingId;
        this.rawText = rawText;
        this.cleanText = cleanText;
        this.formattedText = formattedText;
        this.modelConfidence = modelConfidence;
        this.processedAt = processedAt;
        this.finishedAt = finishedAt;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    private void validate(
            TranscriptionId id,
            MeetingId meetingId,
            String rawText,
            String cleanText,
            TranscriptJson formattedText,
            TranscriptionConfidence modelConfidence,
            LocalDateTime processedAt,
            LocalDateTime finishedAt
    ) {
        ErrorCollector.builder()
                .requireNotNull(id, "transcriptionId", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(meetingId, "meetingId", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(rawText, "rawText", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(cleanText, "cleanText", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(formattedText, "formattedText", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(modelConfidence, "modelConfidence", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(processedAt, "processedAt", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(finishedAt, "finishedAt", DomainErrorCode.EMPTY_FIELD)
                .check(
                        processedAt == null
                                || finishedAt == null
                                || !finishedAt.isBefore(processedAt),
                        DomainError.of(
                                DomainErrorCode.INVALID_RANGE,
                                "finishedAt",
                                "finishedAt cannot be before processedAt"
                        )
                )
                .validate();
    }

    public static Transcription create(
            TranscriptionId id,
            MeetingId meetingId,
            String rawText,
            String cleanText,
            TranscriptJson formattedText,
            TranscriptionConfidence modelConfidence,
            LocalDateTime processedAt,
            LocalDateTime finishedAt
    ) {

        return new Transcription(
                id,
                meetingId,
                rawText,
                cleanText,
                formattedText,
                modelConfidence,
                processedAt,
                finishedAt
        );
    }

    public Transcription update(
            String rawText,
            String cleanText,
            TranscriptJson formattedText,
            TranscriptionConfidence modelConfidence,
            LocalDateTime processedAt,
            LocalDateTime finishedAt
    ) {
        if (rawText != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            rawText,
                            "rawText",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();

            this.rawText = rawText;
        }

        if (cleanText != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            cleanText,
                            "cleanText",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();

            this.cleanText = cleanText;
        }
        if (formattedText != null) {
            this.formattedText = formattedText;
        }
        if (modelConfidence != null) {
            this.modelConfidence = modelConfidence;
        }
        if (processedAt != null) {
            this.processedAt = processedAt;
        }
        if (finishedAt != null) {
            LocalDateTime processReference =
                    processedAt != null
                            ? processedAt
                            : this.processedAt;
            ErrorCollector.builder()
                    .check(
                            !finishedAt.isBefore(processReference),
                            DomainError.of(
                                    DomainErrorCode.INVALID_RANGE,
                                    "finishedAt",
                                    "finishedAt cannot be before processedAt"
                            )
                    )
                    .validate();
            this.finishedAt = finishedAt;
        }
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    public TranscriptionId id() {
        return id;
    }

    public MeetingId meetingId() {
        return meetingId;
    }

    public String rawText() {
        return rawText;
    }

    public String cleanText() {
        return cleanText;
    }

    public TranscriptJson formattedText() {
        return formattedText;
    }

    public TranscriptionConfidence modelConfidence() {
        return modelConfidence;
    }

    public LocalDateTime processedAt() {
        return processedAt;
    }

    public LocalDateTime finishedAt() {
        return finishedAt;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}