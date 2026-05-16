package br.com.meetingdecoder.domain.shared.error;

public class DomainError {
    private final DomainErrorCode code;
    private final String message;

    private DomainError(DomainErrorCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public static DomainError create(DomainErrorCode code, String message) {
        return new DomainError(code, message);
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
