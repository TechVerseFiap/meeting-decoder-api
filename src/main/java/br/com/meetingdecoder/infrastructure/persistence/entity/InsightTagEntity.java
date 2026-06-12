package br.com.meetingdecoder.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "insight_tags")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsightTagEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
