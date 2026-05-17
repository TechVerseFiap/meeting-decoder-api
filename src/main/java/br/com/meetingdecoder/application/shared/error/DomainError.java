package br.com.meetingdecoder.application.shared.error;

public class DomainError {
    private String code;
    private String message;

    private DomainError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static DomainError create(String code, String message) {
        return new DomainError(code, message);
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
