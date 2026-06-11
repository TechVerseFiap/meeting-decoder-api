package br.com.meetingdecoder.application.dto.meeting;

import br.com.meetingdecoder.domain.transcription.enums.MeetingStatus;
import br.com.meetingdecoder.domain.transcription.model.Meeting;

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
    public static MeetingOutput from(Meeting meeting) {
        return new MeetingOutput(
                meeting.id().value(),
                meeting.externalId(),
                meeting.meetingDate(),
                meeting.meetingPeriod().startTime(),
                meeting.meetingPeriod().endTime(),
                meeting.durationInSeconds(),
                meeting.status(),
                meeting.external(),
                meeting.recordingUrl().value(),
                meeting.participants().sellerId().value(),
                meeting.participants().clientId().value(),
                meeting.createdAt(),
                meeting.updatedAt()
        );
    }
}
