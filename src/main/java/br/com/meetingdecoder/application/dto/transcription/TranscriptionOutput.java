package br.com.meetingdecoder.application.dto.transcription;

import br.com.meetingdecoder.domain.transcription.enums.RankingTranscriptionConfidence;

import java.time.LocalDateTime;
import java.util.UUID;

public record TranscriptionOutput(
        UUID id,
        UUID meetingId,
        String rawText,
        String cleanText,
        String formattedText,
        Double confidence,
        RankingTranscriptionConfidence rankingConfidence,
        LocalDateTime processedAt,
        LocalDateTime finishedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
