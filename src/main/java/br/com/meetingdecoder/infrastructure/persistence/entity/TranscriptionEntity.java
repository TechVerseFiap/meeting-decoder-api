package br.com.meetingdecoder.infrastructure.persistence.entity;

import br.com.meetingdecoder.domain.transcription.enums.RankingTranscriptionConfidence;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transcriptions")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranscriptionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "meeting_id", nullable = false)
    private UUID meetingId;

    @Column(name = "raw_text", nullable = false, columnDefinition = "CLOB")
    private String rawText;

    @Column(name = "clean_text", nullable = false, columnDefinition = "CLOB")
    private String cleanText;

    @Column(name = "formatted_text", nullable = false, columnDefinition = "CLOB")
    private String formattedText;

    @Column(name = "model_confidence", nullable = false)
    private Double modelConfidence;

    @Enumerated(EnumType.STRING)
    @Column(name = "ranking_confidence", nullable = false)
    private RankingTranscriptionConfidence rankingConfidence;

    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;

    @Column(name = "finished_at", nullable = false)
    private LocalDateTime finishedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
