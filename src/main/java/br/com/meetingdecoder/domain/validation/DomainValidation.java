package br.com.meetingdecoder.domain.validation;

import br.com.meetingdecoder.domain.exception.EmptyFieldException;
import br.com.meetingdecoder.domain.exception.InvalidDateException;
import br.com.meetingdecoder.domain.exception.InvalidPriceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DomainValidation {

    public static void notBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new EmptyFieldException(fieldName);
        }
    }

    public static void notNull(Object value, String fieldName) {
        if (value == null) {
            throw new EmptyFieldException(fieldName);
        }
    }

    public static void positive(BigDecimal value, String fieldName) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException(fieldName);
        }
    }

    public static void notFuture(LocalDateTime value, String fieldName) {
        if (value == null || value.isAfter(LocalDateTime.now())) {
            throw new InvalidDateException(fieldName);
        }
    }
}
