package br.com.meetingdecoder.domain.transcription.valueobject;

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
        if (sellerId == null || clientId == null) {
            throw new IllegalArgumentException(
                    "Participant must have sellerId and clientId"
            );
        }
    }

    public SellerId sellerId() {
        return sellerId;
    }

    public ClientId clientId() {
        return clientId;
    }
}
