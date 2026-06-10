package br.com.meetingdecoder.domain.insight.valueobject;

import java.util.UUID;

public record InsightId(
        UUID value
) {
    public InsightId {
        if (value == null)
            throw new IllegalArgumentException("");
    }

    public static InsightId of(UUID value) { return new InsightId(value); }
    public static InsightId of(String value) { return new InsightId(UUID.fromString(value)); }
}
