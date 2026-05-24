package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

import java.time.LocalDateTime;
import java.util.UUID;

public class Meeting {
    private UUID id;
    private UUID clientId;
    private UUID sellerId;
    private LocalDateTime timestamp;

    private Meeting(
            UUID clientId,
            UUID sellerId,
            LocalDateTime timestamp
    ) {
        validate(clientId, sellerId, timestamp);

        this.id = UUID.randomUUID();
        this.clientId = clientId;
        this.sellerId = sellerId;
        this.timestamp = timestamp;
    }

    private Meeting(
            UUID clientId,
            UUID sellerId
    ) {
        validate(clientId, sellerId);

        this.id = UUID.randomUUID();
        this.clientId = clientId;
        this.sellerId = sellerId;
        this.timestamp = LocalDateTime.now();
    }

    private void validate(
            UUID clientId,
            UUID sellerId,
            LocalDateTime timestamp
    ) {
        DomainValidation.notNull(clientId, "clientId");
        DomainValidation.notNull(sellerId, "sellerId");
        DomainValidation.notFuture(timestamp, "timestamp");
    }

    private void validate(
            UUID clientId,
            UUID sellerId
    ) {
        DomainValidation.notNull(clientId, "clientId");
        DomainValidation.notNull(sellerId, "sellerId");
    }

    public static Meeting create (
            UUID clientId,
            UUID sellerId,
            LocalDateTime timestamp
    ) {
        return new Meeting (clientId, sellerId, timestamp);
    }

    public static Meeting create (
            UUID clientId,
            UUID sellerId
    ) {
        return new Meeting (clientId, sellerId);
    }
}
