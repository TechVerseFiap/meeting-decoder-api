package br.com.meetingdecoder.domain.exception;

import java.time.LocalDateTime;

public class InvalidDateException extends DomainException {
    public InvalidDateException(String fieldName) {
        super(fieldName + " must be in the past or present.");
    }
}
