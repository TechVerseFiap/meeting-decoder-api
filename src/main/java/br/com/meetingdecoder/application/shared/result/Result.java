package br.com.meetingdecoder.application.shared.result;

import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;

import java.util.List;

public class Result<T> {
    private T data;
    private final ErrorCollector errors;

    private Result(T data, ErrorCollector errors) {
        validate(data, errors);
        this.data = data;
        this.errors = errors;
    }

    private Result(T data, DomainError error) {
        this.data = data;
        this.errors = ErrorCollector.builder()
                .add(error)
                .build();
    }

    private void validate(T data, ErrorCollector errors) {
        boolean hasData = data != null;
        boolean hasErrors = errors != null;

        if (hasData == hasErrors) {
            throw new IllegalStateException(
                    "Result must contain either data or errors"
            );
        }
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, (DomainError) null);
    }

    public static Result<Void> success() {
        return new Result<>(null, (DomainError) null);
    }

    public static <T> Result<T> failure(ErrorCollector errors) {
        return new Result<>(null, errors);
    }

    public static <T> Result<T> failure(DomainError error) {
        return new Result<>(null, error);
    }

    public boolean isSuccess() {
        return !isFailure();
    }

    public boolean isFailure() {
        return errors != null && errors.hasErrors();
    }

    public T getData() {
        return data;
    }

    public List<DomainError> getErrors() {
        return errors.getErrors();
    }
}
