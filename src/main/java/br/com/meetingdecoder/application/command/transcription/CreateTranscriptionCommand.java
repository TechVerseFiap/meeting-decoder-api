package br.com.meetingdecoder.application.command.transcription;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTranscriptionCommand(
        UUID id,
        UUID meetingId,
        String rawText,
        String cleanText,
        String formattedText,
        Double confidence,
        LocalDateTime processedAt,
        LocalDateTime finishedAt
) {
}
