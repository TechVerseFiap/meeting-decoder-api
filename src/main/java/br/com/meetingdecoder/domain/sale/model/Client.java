package br.com.meetingdecoder.domain.sale.model;

import br.com.meetingdecoder.domain.sale.enums.ClientSize;
import br.com.meetingdecoder.domain.sale.enums.ClientType;
import br.com.meetingdecoder.domain.sale.enums.NpsCategory;
import br.com.meetingdecoder.domain.sale.valueobject.BillingRange;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.NpsSnapshot;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

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
        DomainValidation.notNull(id, "id");
        DomainValidation.notBlank(externalId, "externalId");
        DomainValidation.notBlank(corporateReason, "corporateReason");
        DomainValidation.notBlank(fantasyName, "fantasyName");
        DomainValidation.notBlank(cnpj, "cnpj");
        DomainValidation.notBlank(cnae, "cnae");
        DomainValidation.notBlank(segment, "segment");
        DomainValidation.notNull(size, "size");
        DomainValidation.notBlank(city, "city");
        DomainValidation.notBlank(state, "state");
        DomainValidation.notBlank(country, "country");
        DomainValidation.notNull(type, "type");
        DomainValidation.notNull(billingRange, "billingRange");
        DomainValidation.notNull(createdAt, "createdAt");
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
            DomainValidation.notBlank(corporateReason, "corporateReason");
            this.corporateReason = corporateReason;
        }
        if (fantasyName != null) {
            DomainValidation.notBlank(fantasyName, "fantasyName");
            this.fantasyName = fantasyName;
        }
        if (cnpj != null) {
            DomainValidation.notBlank(cnpj, "cnpj");
            this.cnpj = cnpj;
        }
        if (cnae != null) {
            DomainValidation.notBlank(cnae, "cnae");
            this.cnae = cnae;
        }
        if (segment != null) {
            DomainValidation.notBlank(segment, "segment");
            this.segment = segment;
        }
        if (size != null) {
            this.size = size;
        }
        if (city != null) {
            DomainValidation.notBlank(city, "city");
            this.city = city;
        }
        if (state != null) {
            DomainValidation.notBlank(state, "state");
            this.state = state;
        }
        if (country != null) {
            DomainValidation.notBlank(country, "country");
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
