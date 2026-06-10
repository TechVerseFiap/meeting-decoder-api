package br.com.meetingdecoder.domain.sale.model;

import br.com.meetingdecoder.domain.sale.enums.ClientSize;
import br.com.meetingdecoder.domain.sale.enums.ClientType;
import br.com.meetingdecoder.domain.sale.enums.NpsCategory;
import br.com.meetingdecoder.domain.sale.valueobject.BillingRange;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.NpsSnapshot;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

import java.time.LocalDateTime;
import java.util.UUID;

public class Client {
    private final ClientId id;
    private String externalId;
    private String corporateReason;
    private String fantasyName;
    private String cnpj;
    private String cnae;
    private String segment;
    private ClientSize size;
    private String city;
    private String state;
    private String country;
    private ClientType type;
    private BillingRange billingRange;
    private NpsSnapshot npsSnapshot;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Client(
            ClientId id,
            String externalId,
            String corporateReason,
            String fantasyName,
            String cnpj,
            String cnae,
            String segment,
            ClientSize size,
            String city,
            String state,
            String country,
            ClientType type,
            BillingRange billingRange,
            NpsSnapshot npsSnapshot,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        validate(id, externalId, corporateReason, fantasyName, cnpj, cnae, segment,
                size, city, state, country, type, billingRange, createdAt);
        this.id = id;
        this.externalId = externalId;
        this.corporateReason = corporateReason;
        this.fantasyName = fantasyName;
        this.cnpj = cnpj;
        this.cnae = cnae;
        this.segment = segment;
        this.size = size;
        this.city = city;
        this.state = state;
        this.country = country;
        this.type = type;
        this.billingRange = billingRange;
        this.npsSnapshot = npsSnapshot;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validate(
            ClientId id,
            String externalId,
            String corporateReason,
            String fantasyName,
            String cnpj,
            String cnae,
            String segment,
            ClientSize size,
            String city,
            String state,
            String country,
            ClientType type,
            BillingRange billingRange,
            LocalDateTime createdAt
    ) {
        ErrorCollector.builder()
                .requireNotNull(id, "id", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(externalId, "externalId", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(corporateReason, "corporateReason", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(fantasyName, "fantasyName", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(cnpj, "cnpj", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(cnae, "cnae", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(segment, "segment", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(size, "size", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(city, "city", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(state, "state", DomainErrorCode.EMPTY_FIELD)
                .requireNotBlank(country, "country", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(type, "type", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(billingRange, "billingRange", DomainErrorCode.EMPTY_FIELD)
                .requireNotNull(createdAt, "createdAt", DomainErrorCode.EMPTY_FIELD)
                .validate();
    }

    public static Client create(
            ClientId id,
            String externalId,
            String corporateReason,
            String fantasyName,
            String cnpj,
            String cnae,
            String segment,
            String city,
            String state,
            String country,
            ClientType type,
            BillingRange billingRange,
            NpsSnapshot npsSnapshot
    ) {
        return new Client(
                id,
                externalId,
                corporateReason,
                fantasyName,
                cnpj,
                cnae,
                segment,
                determineSize(billingRange),
                city,
                state,
                country,
                type,
                billingRange,
                npsSnapshot,
                LocalDateTime.now(),
                null
        );
    }

    private static ClientSize determineSize(BillingRange billingRange) {
        if (billingRange == null) {
            return ClientSize.INDETERMINATE;
        }
        double max = billingRange.maxValue();
        if (max <= 360000) {
            return ClientSize.MICRO;
        }
        if (max <= 4800000) {
            return ClientSize.SMALL;
        }
        if (max <= 300000000) {
            return ClientSize.MEDIUM;
        }
        return ClientSize.LARGE;
    }

    public Client update(
            String corporateReason,
            String fantasyName,
            String cnpj,
            String cnae,
            String segment,
            ClientSize size,
            String city,
            String state,
            String country,
            ClientType type,
            BillingRange billingRange,
            NpsSnapshot npsSnapshot
    ) {
        if (corporateReason != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            corporateReason,
                            "corporateReason",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();
            this.corporateReason = corporateReason;
        }
        if (fantasyName != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            fantasyName,
                            "fantasyName",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();
            this.fantasyName = fantasyName;
        }
        if (cnpj != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            cnpj,
                            "cnpj",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();
            this.cnpj = cnpj;
        }
        if (cnae != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            cnae,
                            "cnae",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();
            this.cnae = cnae;
        }
        if (segment != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            segment,
                            "segment",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();
            this.segment = segment;
        }
        if (size != null) {
            this.size = size;
        }
        if (city != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            city,
                            "city",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();
            this.city = city;
        }
        if (state != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            state,
                            "state",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();
            this.state = state;
        }
        if (country != null) {
            ErrorCollector.builder()
                    .requireNotBlank(
                            country,
                            "country",
                            DomainErrorCode.EMPTY_FIELD
                    )
                    .validate();
            this.country = country;
        }
        if (type != null) {
            this.type = type;
        }
        if (billingRange != null) {
            this.billingRange = billingRange;
        }
        if (npsSnapshot != null) {
            this.npsSnapshot = npsSnapshot;
        }
        return this;
    }

    public UUID getId() {
        return id.value();
    }

    public String getExternalId() {
        return externalId;
    }

    public String getCorporateReason() {
        return corporateReason;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCnae() {
        return cnae;
    }

    public String getSegment() {
        return segment;
    }

    public ClientSize getSize() {
        return size;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public ClientType getType() {
        return type;
    }

    public double getMinBilling() {
        return billingRange.minValue();
    }

    public double getMaxBilling() {
        return billingRange.maxValue();
    }

    public double getNpsNote() {
        return npsSnapshot.npsNote();
    }

    public LocalDateTime getNpsDate() {
        return npsSnapshot.npsDate();
    }

    public NpsCategory getNpsCategory() {
        return npsSnapshot.category();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
