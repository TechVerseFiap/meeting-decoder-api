package br.com.meetingdecoder.application.command.meeting;

import br.com.meetingdecoder.domain.transcription.enums.MeetingStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateMeetingCommand(
        UUID id,
        String externalId,
        LocalDateTime meetingDate,
        LocalDateTime startTime,
        LocalDateTime endTime,
        MeetingStatus status,
        Boolean external,
        String recordingUrl,
        UUID sellerId,
        UUID clientId
) {
}
