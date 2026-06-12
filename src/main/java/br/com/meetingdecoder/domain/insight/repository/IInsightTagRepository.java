package br.com.meetingdecoder.domain.insight.repository;

import br.com.meetingdecoder.domain.insight.model.InsightTag;
import br.com.meetingdecoder.domain.insight.valueobject.InsightTagId;

import java.util.List;
import java.util.Optional;

public interface IInsightTagRepository {
    Optional<InsightTag> findById(InsightTagId id);

    Optional<InsightTag> findByNome(String nome);

    List<InsightTag> findAll();
}
