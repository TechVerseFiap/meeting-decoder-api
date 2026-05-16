package br.com.meetingdecoder.domain.shared.error;

public class DomainErrorFactory {
    private DomainErrorFactory() {}

    public static DomainError emptyField(String fieldName) {
        return DomainError.create(DomainErrorCode.EMPTY_FIELD, "Field '" + fieldName + "' must not be blank");
    }

    public static DomainError invalidPrice(String fieldName) {
        return DomainError.create(DomainErrorCode.INVALID_PRICE, "Field '" + fieldName + "' must be positive");
    }

    public static DomainError invalidDate(String fieldName) {
        return DomainError.create(DomainErrorCode.INVALID_DATE, "Field '" + fieldName + "' must not be in the future");
    }

    public static DomainError alreadyExists(String entity) {
        return DomainError.create(DomainErrorCode.ALREADY_EXISTS, entity + " already exists");
    }

    public static DomainError notFound(String entity) {
        return DomainError.create(DomainErrorCode.NOT_FOUND, entity + " not found");
    }
}
