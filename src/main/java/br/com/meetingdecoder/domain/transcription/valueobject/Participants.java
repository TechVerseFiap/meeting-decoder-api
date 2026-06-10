package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

public class Participants {
    private final SellerId sellerId;
    private final ClientId clientId;

    private Participants(
            SellerId sellerId,
            ClientId clientId
    ) {
        validate(sellerId, clientId);
        this.sellerId = sellerId;
        this.clientId = clientId;
    }

    public static Participants of(
            SellerId sellerId,
            ClientId clientId
    ) {
        return new Participants(sellerId, clientId);
    }

    private void validate(
            SellerId sellerId,
            ClientId clientId
    ) {
        ErrorCollector.builder()
                .requireNotNull(
                        sellerId,
                        "sellerId",
                        DomainErrorCode.EMPTY_FIELD
                )
                .requireNotNull(
                        clientId,
                        "clientId",
                        DomainErrorCode.EMPTY_FIELD
                )
                .validate();
    }

    public SellerId sellerId() {
        return sellerId;
    }

    public ClientId clientId() {
        return clientId;
    }
}
