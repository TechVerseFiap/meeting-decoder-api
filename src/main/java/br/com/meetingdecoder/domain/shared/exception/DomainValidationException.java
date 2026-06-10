package br.com.meetingdecoder.domain.shared.exception;

import br.com.meetingdecoder.domain.shared.validation.DomainError;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DomainValidationException extends RuntimeException {
    private final List<DomainError> errors;

    public DomainValidationException(List<DomainError> errors) {
        super(buildMessage(errors));

        if (errors == null || errors.isEmpty())
            throw new IllegalArgumentException("DomainValidationException requires at least one error");

        this.errors = List.copyOf(errors);
    }

    public List<DomainError> getErrors() {
        return errors;
    }

    public boolean hasErrorCode(String code) {
        return errors.stream()
                .anyMatch(error -> error.code().equals(code));
    }

    public boolean hasField(String field) {
        return errors.stream()
                .anyMatch(error -> Objects.equals(error.field(), field));
    }

    private static String buildMessage(List<DomainError> errors) {
        if (errors == null || errors.isEmpty()) {
            return "Domain validation failed";
        }

        return errors.stream()
                .map(error -> {
                    String fieldPart = error.field() != null
                            ? "[" + error.field() + "] "
                            : "";

                    return fieldPart
                            + error.code()
                            + " - "
                            + error.message();
                })
                .collect(Collectors.joining("; "));
    }
}