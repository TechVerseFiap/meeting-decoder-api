package br.com.meetingdecoder.infrastructure.persistence.jpa;

import br.com.meetingdecoder.infrastructure.persistence.entity.TranscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaTranscriptionRepository extends JpaRepository<TranscriptionEntity, UUID> {

    List<TranscriptionEntity> findAllByMeetingId(UUID meetingId);
}
