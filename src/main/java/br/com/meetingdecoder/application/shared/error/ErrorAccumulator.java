package br.com.meetingdecoder.application.shared.error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorAccumulator {
    private final List<DomainError> errors = new ArrayList<>();

    public ErrorAccumulator() {}

    public ErrorAccumulator(DomainError error) {
        add(error);
    }

    public void add(DomainError error) {
        errors.add(error);
    }

    public void addIf(boolean condition, DomainError error) {
        if (condition) {
            add(error);
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<DomainError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
