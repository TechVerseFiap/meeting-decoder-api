package br.com.meetingdecoder.infrastructure.persistence.entity;

import br.com.meetingdecoder.domain.transcription.enums.MeetingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "meetings")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "meeting_date", nullable = false)
    private LocalDateTime meetingDate;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "duration_in_seconds")
    private Long durationInSeconds;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MeetingStatus status;

    @Column(name = "external", nullable = false)
    private Boolean external;

    @Column(name = "recording_url", nullable = false)
    private String recordingUrl;

    @Column(name = "seller_id", nullable = false)
    private UUID sellerId;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
