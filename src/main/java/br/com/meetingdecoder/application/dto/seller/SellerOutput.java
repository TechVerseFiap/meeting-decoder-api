package br.com.meetingdecoder.application.dto.seller;

import br.com.meetingdecoder.domain.sale.enums.SellerType;
import br.com.meetingdecoder.domain.sale.model.Seller;

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
    public static SellerOutput from(Seller seller) {
        return new SellerOutput(
                seller.id(),
                seller.managerId(),
                seller.type(),
                seller.name(),
                seller.email(),
                seller.active(),
                seller.createdAt(),
                seller.updatedAt()
        );
    }
}
