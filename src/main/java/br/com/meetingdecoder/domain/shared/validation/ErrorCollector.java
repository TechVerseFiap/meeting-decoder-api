package br.com.meetingdecoder.domain.shared.validation;

import br.com.meetingdecoder.domain.shared.exception.DomainValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ErrorCollector {
    private final List<DomainError> errors;

    private ErrorCollector(List<DomainError> errors) {
        this.errors = errors;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<DomainError> errors = new ArrayList<>();

        public Builder add(DomainError error) {
            Objects.requireNonNull(error);
            errors.add(error);
            return this;
        }

        public Builder check(
                boolean condition,
                DomainError error
        ) {
            if (!condition)
                add(error);
            return this;
        }

        public Builder requireNotNull(
                Object value,
                String field,
                DomainErrorCode code
        ) {
            if (value == null)
                add(DomainError.of(code, field));
            return this;
        }

        public Builder requireNotBlank(
                String value,
                String field,
                DomainErrorCode code
        ) {
            if (value == null || value.isBlank())
                add(DomainError.of(code, field));
            return this;
        }

        /**
         * Validate numeric value is in closed range [min, max].
         * If value is null, adds error. If out of range, adds error.
         *
         * @param value the numeric value to validate
         * @param min minimum inclusive bound
         * @param max maximum inclusive bound
         * @param field field name for error reporting
         * @param code error code
         * @return this for chaining
         */
        public Builder requireInRange(
                Double value,
                double min,
                double max,
                String field,
                DomainErrorCode code
        ) {
            if (value == null) {
                add(DomainError.of(code, field, field + " is null"));
                return this;
            }
            if (value < min || value > max) {
                add(DomainError.of(code, field, field + " must be between " + min + " and " + max));
            }
            return this;
        }

        /**
         * Validate that BigDecimal minimum is less than or equal to maximum.
         * Both values optional independently; only validates when both non-null.
         *
         * @param min minimum value
         * @param max maximum value
         * @param fieldMin field name for minimum (error reporting)
         * @param fieldMax field name for maximum (error reporting)
         * @param code error code
         * @return this for chaining
         */
        public Builder requireMinLessOrEqualMax(
                BigDecimal min,
                BigDecimal max,
                String fieldMin,
                String fieldMax,
                DomainErrorCode code
        ) {
            if (min != null && max != null) {
                if (min.compareTo(max) > 0) {
                    add(DomainError.of(code, fieldMin, fieldMin + " must be <= " + fieldMax));
                }
            }
            return this;
        }

        /**
         * Null-safe numeric not-null check for Double wrappers.
         *
         * @param value the Double value
         * @param field field name for error reporting
         * @param code error code
         * @return this for chaining
         */
        public Builder requireDoubleNotNull(
                Double value,
                String field,
                DomainErrorCode code
        ) {
            if (value == null) {
                add(DomainError.of(code, field, field + " is required"));
            }
            return this;
        }

        public Builder merge(ErrorCollector other) {
            if (other == null) return this;
            errors.addAll(other.errors);
            return this;
        }

        public ErrorCollector build() {
            return new ErrorCollector(errors);
        }

        public void validate() {
            new ErrorCollector(errors).validate();
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<DomainError> getErrors() {
        return List.copyOf(errors);
    }

    public void validate() {
        if (hasErrors())
            throw new DomainValidationException(getErrors());
    }

    public Map<String, List<DomainError>> byField() {
        return errors.stream().collect(Collectors.groupingBy(DomainError::field));
    }

    public Map<String, List<DomainError>> byCode() {
        return errors.stream().collect(Collectors.groupingBy(DomainError::code));
    }
}
