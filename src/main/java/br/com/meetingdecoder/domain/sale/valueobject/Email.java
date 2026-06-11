package br.com.meetingdecoder.domain.sale.valueobject;

import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

public class Email {
    private final String value;

    private Email(String value) {
        ErrorCollector.builder()
                .requireNotBlank(
                        value,
                        "email",
                        DomainErrorCode.EMPTY_FIELD
                )
                .validate();
        String normalizedValue = value.trim().toLowerCase();
        validate(normalizedValue);
        this.value = normalizedValue;
    }

    private void validate(String value) {
        ErrorCollector.builder()
                .requireNotBlank(
                        value,
                        "email",
                        DomainErrorCode.EMPTY_FIELD
                )
                .check(
                        value == null || isValid(value),
                        DomainError.of(
                                DomainErrorCode.INVALID_EMAIL,
                                "email"
                        )
                )
                .validate();
    }

    private boolean isValid(String value) {
        return value.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static Email of(String value) {
        return new Email(value);
    }

    public String value() { return value; }
}
