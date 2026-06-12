package br.com.meetingdecoder.domain.transcription.model;

import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;
import br.com.meetingdecoder.domain.transcription.enums.MeetingStatus;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingId;
import br.com.meetingdecoder.domain.transcription.valueobject.MeetingPeriod;
import br.com.meetingdecoder.domain.transcription.valueobject.Participants;
import br.com.meetingdecoder.domain.transcription.valueobject.RecordingUrl;

import java.time.LocalDateTime;

public class Meeting {
    private final MeetingId id;
    private String externalId;
    private LocalDateTime meetingDate;
    private MeetingPeriod meetingPeriod;
    private Long durationInSeconds;
    private MeetingStatus status;
    private Boolean external;
    private RecordingUrl recordingUrl;
    private Participants participants;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Meeting(
            MeetingId id,
            String externalId,
            LocalDateTime meetingDate,
            MeetingPeriod meetingPeriod,
            MeetingStatus status,
            Boolean external,
            RecordingUrl recordingUrl,
            Participants participants
    ) {
        validate(
                id,
                externalId,
                meetingDate,
                meetingPeriod,
                status,
                external,
                recordingUrl,
                participants
        );
        this.id = id;
        this.externalId = externalId;
        this.meetingDate = meetingDate;
        this.meetingPeriod = meetingPeriod;
        this.durationInSeconds = meetingPeriod.durationInSeconds();
        this.status = status;
        this.external = external;
        this.recordingUrl = recordingUrl;
        this.participants = participants;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    private void validate(
            MeetingId id,
            String externalId,
            LocalDateTime meetingDate,
            MeetingPeriod meetingPeriod,
            MeetingStatus status,
            Boolean external,
            RecordingUrl recordingUrl,
            Participants participants
    ) {
        ErrorCollector.builder()
                .requireNotNull(id, "meetingId", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(externalId, "externalId", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(meetingDate, "meetingDate", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(meetingPeriod, "meetingPeriod", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(status, "status", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(external, "external", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(recordingUrl, "recordingUrl", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(participants, "participants", DomainErrorCode.EMPTY_FIELD)
                .validate();
    }

    public static Meeting create(
            MeetingId id,
            String externalId,
            LocalDateTime meetingDate,
            MeetingPeriod meetingPeriod,
            MeetingStatus status,
            Boolean external,
            RecordingUrl recordingUrl,
            Participants participants
    ) {
        return new Meeting(
                id,
                externalId,
                meetingDate,
                meetingPeriod,
                status,
                external,
                recordingUrl,
                participants
        );
    }

    public Meeting update(
            String externalId,
            LocalDateTime meetingDate,
            MeetingPeriod meetingPeriod,
            MeetingStatus status,
            Boolean external,
            RecordingUrl recordingUrl,
            Participants participants
    ) {
        if (externalId != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            externalId,
                            "externalId",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();

            this.externalId = externalId;
        }
        if (meetingDate != null) {
            this.meetingDate = meetingDate;
        }
        if (meetingPeriod != null) {
            this.meetingPeriod = meetingPeriod;
            this.durationInSeconds = meetingPeriod.durationInSeconds();
        }
        if (status != null) {
            this.status = status;
        }
        if (external != null) {
            this.external = external;
        }
        if (recordingUrl != null) {
            this.recordingUrl = recordingUrl;
        }
        if (participants != null) {
            this.participants = participants;
        }
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    public MeetingId id() {
        return id;
    }

    public String externalId() {
        return externalId;
    }

    public LocalDateTime meetingDate() {
        return meetingDate;
    }

    public MeetingPeriod meetingPeriod() {
        return meetingPeriod;
    }

    public Long durationInSeconds() {
        return durationInSeconds;
    }

    public MeetingStatus status() {
        return status;
    }

    public Boolean external() {
        return external;
    }

    public RecordingUrl recordingUrl() {
        return recordingUrl;
    }

    public Participants participants() {
        return participants;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
