package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;
import br.com.meetingdecoder.domain.transcription.enums.RankingTranscriptionConfidence;

public class TranscriptionConfidence {
    private static final Double GOOD_CONFIDENCE = 0.7;
    private static final Double BAD_CONFIDENCE = 0.4;
    private final Double value;
    private final RankingTranscriptionConfidence rankingConfidence;

    private TranscriptionConfidence(Double value) {
        validate(value);
        this.value = value;
        this.rankingConfidence = getRankingConfidence();
    }

    private void validate(Double value) {
        ErrorCollector.builder()
                .requireInRange(
                        value,
                        0.0,
                        1.0,
                        "value",
                        DomainErrorCode.INVALID_SCORE
                )
                .validate();
    }

    private RankingTranscriptionConfidence getRankingConfidence() {
        if (value >= GOOD_CONFIDENCE)
            return RankingTranscriptionConfidence.HIGH;
        if (value <= BAD_CONFIDENCE)
            return RankingTranscriptionConfidence.LOW;
        return RankingTranscriptionConfidence.MEDIUM;
    }

    public static TranscriptionConfidence of(Double value) {
        return new TranscriptionConfidence(value);
    }

    public Double value() {
        return value;
    }

    public RankingTranscriptionConfidence rankingConfidence() {
        return rankingConfidence;
    }
}
