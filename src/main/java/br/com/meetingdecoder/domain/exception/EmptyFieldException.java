package br.com.meetingdecoder.domain.exception;

public class EmptyFieldException extends DomainException {
    public EmptyFieldException(String fieldName) {
        super(fieldName + " cannot be blank.");
    }
}
