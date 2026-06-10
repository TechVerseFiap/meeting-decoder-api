package br.com.meetingdecoder.domain.shared.validation;

public record DomainError(
        String code,
        String field,
        String message
) {
    public static DomainError of(
            String code,
            String field,
            String message
    ) {
        return new DomainError(code, field, message);
    }

    public static DomainError of(
            DomainErrorCode code,
            String field,
            String message
    ) {
        return new DomainError(code.toString(), field, message);
    }

    public static DomainError of(
            DomainErrorCode code,
            String field
    ) {
        return new DomainError(
                code.toString(),
                field,
                getMessage(code, field)
        );
    }

    private static String getMessage(DomainErrorCode code, String field) {
        String message = switch (code) {
            case EMPTY_FIELD -> "vazio";
            case INVALID_PRICE -> "com valor inválido";
            case INVALID_DATE -> "com data inválida";
            case INVALID_RANGE -> "com intervalo inválido";
            case INVALID_SCORE -> "com score inválido";
            case ALREADY_EXISTS -> "já existe";
            case NOT_FOUND -> "não encontrado";
        };

        return "Campo " + field + " " + message;
    }
}
