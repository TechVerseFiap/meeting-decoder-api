package br.com.meetingdecoder.domain.shared.exception;

public class InvalidDateException extends DomainException {
    public InvalidDateException(String fieldName) {
        super(fieldName + " must be in the past or present.");
    }
}
