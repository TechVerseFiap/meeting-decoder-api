package br.com.meetingdecoder.domain.insight.model;

import br.com.meetingdecoder.domain.insight.valueobject.InsightTagId;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;
import java.time.Instant;
import java.util.UUID;

public final class InsightTag {
    private final InsightTagId id;
    private final String nome;
    private final Instant createdAt;

    private InsightTag(InsightTagId id, String nome, Instant createdAt) {
        this.id = id;
        this.nome = nome;
        this.createdAt = createdAt;
    }

    public static InsightTag restore(InsightTagId id, String nome, Instant createdAt) {
        return new InsightTag(id, nome, createdAt);
    }

    public static InsightTag create(String nome) {
        ErrorCollector.builder()
                .requireNotNull(nome, "nome", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(nome, "nome", DomainErrorCode.EMPTY_FIELD)
                .validate();

        InsightTagId id = InsightTagId.of(UUID.randomUUID());
        Instant now = Instant.now();
        return new InsightTag(id, nome, now);
    }

    public InsightTag update(String nome) {
        ErrorCollector.builder()
                .requireNotBlank(
                        nome,
                        "nome",
                        DomainErrorCode.EMPTY_FIELD
                )
                .validate();
        return new InsightTag(
                this.id,
                nome,
                this.createdAt
        );
    }

    public InsightTagId getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
