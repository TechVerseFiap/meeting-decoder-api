package br.com.meetingdecoder.domain.sale.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainValidation;

public class Email {
    private final String value;

    private Email(String value) {
        String normalizedValue = value.trim().toLowerCase();
        validate(normalizedValue);
        this.value = normalizedValue;
    }

    private void validate(String value) {
        DomainValidation.notBlank(value, "email");
        if (!isValid(value))
            throw new IllegalArgumentException("The email should follow the pattern abc123@domain.com: " + value);
    }

    private boolean isValid(String value) {
        return value.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static Email of(String value) {
        return new Email(value);
    }

    public String value() { return value; }
}
