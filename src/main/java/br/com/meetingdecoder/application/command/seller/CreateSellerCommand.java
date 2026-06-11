package br.com.meetingdecoder.application.command.seller;

import br.com.meetingdecoder.domain.sale.enums.SellerType;

import java.util.UUID;

public record CreateSellerCommand(
        UUID id,
        UUID managerId,
        SellerType type,
        String name,
        String email
) {
}
