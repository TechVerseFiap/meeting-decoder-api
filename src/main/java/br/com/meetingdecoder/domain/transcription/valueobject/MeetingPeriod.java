package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

import java.time.Duration;
import java.time.LocalDateTime;

public class MeetingPeriod {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private MeetingPeriod(LocalDateTime startTime, LocalDateTime endTime) {
        validate(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validate(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        ErrorCollector.builder()
                .requireNotNull(
                        startTime,
                        "startTime",
                        DomainErrorCode.EMPTY_FIELD
                )
                .requireNotNull(
                        endTime,
                        "endTime",
                        DomainErrorCode.EMPTY_FIELD
                )
                .check(
                        startTime == null
                                || !startTime.isAfter(LocalDateTime.now()),
                        DomainError.of(
                                DomainErrorCode.INVALID_FIELD,
                                "startTime",
                                "startTime cannot be in the future"
                        )
                )
                .check(
                        endTime == null
                                || !endTime.isAfter(LocalDateTime.now()),
                        DomainError.of(
                                DomainErrorCode.INVALID_FIELD,
                                "endTime",
                                "endTime cannot be in the future"
                        )
                )
                .check(
                        startTime == null
                                || endTime == null
                                || !endTime.isBefore(startTime),
                        DomainError.of(
                                DomainErrorCode.INVALID_FIELD,
                                "endTime",
                                "endTime cannot be before startTime"
                        )
                )
                .validate();
    }

    public static MeetingPeriod of(LocalDateTime startTime, LocalDateTime endTime) {
        return new MeetingPeriod(startTime, endTime);
    }

    public long durationInSeconds() {
        return Duration.between(startTime, endTime).getSeconds();
    }

    public long durationInMinutes() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    public LocalDateTime startTime() {
        return this.startTime;
    }

    public LocalDateTime endTime() {
        return this.endTime;
    }
}
