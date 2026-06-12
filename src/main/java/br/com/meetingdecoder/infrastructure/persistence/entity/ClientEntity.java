package br.com.meetingdecoder.infrastructure.persistence.entity;

import br.com.meetingdecoder.domain.sale.enums.ClientSize;
import br.com.meetingdecoder.domain.sale.enums.ClientType;
import br.com.meetingdecoder.domain.sale.enums.NpsCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "corporate_reason", nullable = false)
    private String corporateReason;

    @Column(name = "fantasy_name", nullable = false)
    private String fantasyName;

    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @Column(name = "cnae", nullable = false)
    private String cnae;

    @Column(name = "segment", nullable = false)
    private String segment;

    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private ClientSize size;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "country", nullable = false)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ClientType type;

    @Column(name = "billing_min", nullable = false)
    private double billingMin;

    @Column(name = "billing_max", nullable = false)
    private double billingMax;

    @Column(name = "nps_note")
    private Double npsNote;

    @Column(name = "nps_date")
    private LocalDateTime npsDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "nps_category")
    private NpsCategory npsCategory;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
