package br.com.meetingdecoder.application.dto.seller;

import br.com.meetingdecoder.domain.sale.enums.SellerType;

import java.time.LocalDateTime;
import java.util.UUID;

public record SellerOutput(
        UUID id,
        UUID managerId,
        SellerType type,
        String name,
        String email,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
