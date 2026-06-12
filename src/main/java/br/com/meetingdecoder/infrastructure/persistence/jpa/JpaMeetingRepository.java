package br.com.meetingdecoder.infrastructure.persistence.jpa;

import br.com.meetingdecoder.infrastructure.persistence.entity.MeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMeetingRepository extends JpaRepository<MeetingEntity, UUID> {

    boolean existsByExternalId(String externalId);
}
