package br.com.meetingdecoder.infrastructure.persistence.repository;

import br.com.meetingdecoder.domain.transcription.model.Transcription;
import br.com.meetingdecoder.domain.transcription.repository.ITranscriptionRepository;
import br.com.meetingdecoder.domain.transcription.valueobject.*;
import br.com.meetingdecoder.infrastructure.persistence.entity.TranscriptionEntity;
import br.com.meetingdecoder.infrastructure.persistence.jpa.JpaTranscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TranscriptionRepositoryAdapter implements ITranscriptionRepository {

    private final JpaTranscriptionRepository jpa;

    @Override
    public Transcription save(Transcription transcription) {
        TranscriptionEntity entity = toEntity(transcription);
        TranscriptionEntity saved = jpa.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsById(TranscriptionId id) {
        return jpa.existsById(id.value());
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public List<Transcription> findAll() {
        return jpa.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Transcription> findById(TranscriptionId id) {
        return jpa.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public void deleteById(TranscriptionId id) {
        jpa.deleteById(id.value());
    }

    private TranscriptionEntity toEntity(Transcription transcription) {
        return TranscriptionEntity.builder()
                .id(transcription.id().value())
                .meetingId(transcription.meetingId().value())
                .rawText(transcription.rawText())
                .cleanText(transcription.cleanText())
                .formattedText(transcription.formattedText().value())
                .modelConfidence(transcription.modelConfidence().value())
                .rankingConfidence(transcription.modelConfidence().rankingConfidence())
                .processedAt(transcription.processedAt())
                .finishedAt(transcription.finishedAt())
                .createdAt(transcription.createdAt())
                .updatedAt(transcription.updatedAt())
                .build();
    }

    private Transcription toDomain(TranscriptionEntity entity) {
        return Transcription.create(
                TranscriptionId.of(entity.getId()),
                MeetingId.of(entity.getMeetingId()),
                entity.getRawText(),
                entity.getCleanText(),
                TranscriptJson.of(entity.getFormattedText()),
                TranscriptionConfidence.of(entity.getModelConfidence()),
                entity.getProcessedAt(),
                entity.getFinishedAt()
        );
    }
}
