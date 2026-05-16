package br.com.meetingdecoder.domain.shared.result;

import br.com.meetingdecoder.domain.shared.error.DomainError;
import br.com.meetingdecoder.domain.shared.error.ErrorAccumulator;

import java.util.List;

public class Result<T> {
    private T data;
    private final ErrorAccumulator errors;

    private Result(T data, ErrorAccumulator errors) {
        this.data = data;
        this.errors = errors;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null);
    }

    public static <T> Result<T> failure(ErrorAccumulator errors) {
        return new Result<>(null, errors);
    }

    public boolean isSuccess() {
        return errors == null;
    }

    public boolean isFailure() {
        return errors.hasErrors();
    }

    public T getData() {
        return data;
    }

    public List<DomainError> getErrors() {
        return errors.getErrors();
    }
}
