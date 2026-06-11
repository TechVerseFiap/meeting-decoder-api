package br.com.meetingdecoder.domain.shared.exception;

public class EmptyFieldException extends DomainException {
    public EmptyFieldException(String fieldName) {
        super(fieldName + " cannot be blank.");
    }
}
