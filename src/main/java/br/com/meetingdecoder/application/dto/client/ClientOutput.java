package br.com.meetingdecoder.application.dto.client;

import br.com.meetingdecoder.domain.sale.enums.ClientSize;
import br.com.meetingdecoder.domain.sale.enums.ClientType;
import br.com.meetingdecoder.domain.sale.enums.NpsCategory;
import br.com.meetingdecoder.domain.sale.model.Client;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientOutput(
        UUID id,
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
        double minBilling,
        double maxBilling,
        double npsNote,
        LocalDateTime npsDate,
        NpsCategory npsCategory,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ClientOutput from(Client client) {
        return new ClientOutput(
                client.id(),
                client.externalId(),
                client.corporateReason(),
                client.fantasyName(),
                client.cnpj(),
                client.cnae(),
                client.segment(),
                client.size(),
                client.city(),
                client.state(),
                client.country(),
                client.type(),
                client.minBilling(),
                client.maxBilling(),
                client.npsNote(),
                client.npsDate(),
                client.npsCategory(),
                client.createdAt(),
                client.updatedAt()
        );
    }
}
