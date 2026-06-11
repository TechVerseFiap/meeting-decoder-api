package br.com.meetingdecoder.application.command.client;

import br.com.meetingdecoder.domain.sale.enums.ClientType;
import br.com.meetingdecoder.domain.sale.valueobject.BillingRange;
import br.com.meetingdecoder.domain.sale.valueobject.NpsSnapshot;

import java.util.UUID;

public record CreateClientCommand(
        UUID id,
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
}
