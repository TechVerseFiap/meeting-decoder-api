package br.com.meetingdecoder.application.dto.transcription;

import br.com.meetingdecoder.domain.transcription.enums.RankingTranscriptionConfidence;
import br.com.meetingdecoder.domain.transcription.model.Transcription;

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
    public static TranscriptionOutput from(Transcription transcription) {
        return new TranscriptionOutput(
                transcription.id().value(),
                transcription.meetingId().value(),
                transcription.rawText(),
                transcription.cleanText(),
                transcription.formattedText().value(),
                transcription.modelConfidence().value(),
                transcription.modelConfidence().rankingConfidence(),
                transcription.processedAt(),
                transcription.finishedAt(),
                transcription.createdAt(),
                transcription.updatedAt()
        );
    }
}
