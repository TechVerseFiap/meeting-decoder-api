package br.com.meetingdecoder.domain.transcription.model;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;
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
        DomainValidation.notNull(id, "meetingId");
        DomainValidation.notBlank(externalId, "externalId");
        DomainValidation.notNull(meetingDate, "meetingDate");
        DomainValidation.notNull(meetingPeriod, "meetingPeriod");
        DomainValidation.notNull(status, "status");
        DomainValidation.notNull(external, "external");
        DomainValidation.notNull(recordingUrl, "recordingUrl");
        DomainValidation.notNull(participants, "participants");
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
            DomainValidation.notBlank(externalId, "externalId");
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
