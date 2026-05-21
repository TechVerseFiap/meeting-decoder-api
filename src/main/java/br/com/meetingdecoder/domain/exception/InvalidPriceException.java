package br.com.meetingdecoder.domain.exception;

public class InvalidPriceException extends DomainException {
    public InvalidPriceException(String fieldName) {
        super(fieldName + " must be positive.");
    }
}
