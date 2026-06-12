package br.com.meetingdecoder.infrastructure.persistence.repository;

import br.com.meetingdecoder.domain.insight.model.InsightTag;
import br.com.meetingdecoder.domain.insight.repository.IInsightTagRepository;
import br.com.meetingdecoder.domain.insight.valueobject.InsightTagId;
import br.com.meetingdecoder.infrastructure.persistence.entity.InsightTagEntity;
import br.com.meetingdecoder.infrastructure.persistence.jpa.JpaInsightTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InsightTagRepositoryAdapter implements IInsightTagRepository {

    private final JpaInsightTagRepository jpa;

    @Override
    public Optional<InsightTag> findById(InsightTagId id) {
        return jpa.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public Optional<InsightTag> findByNome(String nome) {
        return jpa.findByNome(nome)
                .map(this::toDomain);
    }

    @Override
    public List<InsightTag> findAll() {
        return jpa.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    private InsightTag toDomain(InsightTagEntity entity) {
        return InsightTag.restore(
                InsightTagId.of(entity.getId()),
                entity.getNome(),
                entity.getCreatedAt()
        );
    }
}
