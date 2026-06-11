package br.com.meetingdecoder.application.dto.meeting;

import br.com.meetingdecoder.domain.transcription.enums.MeetingStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record MeetingOutput(
        UUID id,
        String externalId,
        LocalDateTime meetingDate,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long durationInSeconds,
        MeetingStatus status,
        Boolean external,
        String recordingUrl,
        UUID sellerId,
        UUID clientId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
