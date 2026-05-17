package br.com.meetingdecoder.application.shared.error;

public class DomainErrorFactory {
    private DomainErrorFactory() {}

    public static DomainError emptyField(String fieldName) {
        return DomainError.create("EMPTY_FIELD", "Field '" + fieldName + "' must not be blank");
    }

    public static DomainError invalidPrice(String fieldName) {
        return DomainError.create("INVALID_PRICE", "Field '" + fieldName + "' must be positive");
    }

    public static DomainError invalidDate(String fieldName) {
        return DomainError.create("INVALID_DATE", "Field '" + fieldName + "' must not be in the future");
    }

    public static DomainError alreadyExists(String entity) {
        return DomainError.create("ALREADY_EXISTS", entity + " already exists");
    }

    public static DomainError notFound(String entity) {
        return DomainError.create("NOT_FOUND", entity + " not found");
    }
}
