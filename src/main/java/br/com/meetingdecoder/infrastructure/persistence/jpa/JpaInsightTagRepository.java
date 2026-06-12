package br.com.meetingdecoder.infrastructure.persistence.jpa;

import br.com.meetingdecoder.infrastructure.persistence.entity.InsightTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaInsightTagRepository extends JpaRepository<InsightTagEntity, UUID> {

    Optional<InsightTagEntity> findByNome(String nome);
}
