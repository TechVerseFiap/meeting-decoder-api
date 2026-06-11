package br.com.meetingdecoder.domain.shared.exception;

public class InvalidPriceException extends DomainException {
    public InvalidPriceException(String fieldName) {
        super(fieldName + " must be positive.");
    }
}
