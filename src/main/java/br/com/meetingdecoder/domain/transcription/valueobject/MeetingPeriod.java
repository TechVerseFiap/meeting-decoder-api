package br.com.meetingdecoder.domain.transcription.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

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

    private void validate(LocalDateTime startTime, LocalDateTime endTime) {
        DomainValidation.notFuture(startTime, "startTime");
        DomainValidation.notFuture(endTime, "endTime");
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("endTime cannot be before startTime");
        }
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
}
