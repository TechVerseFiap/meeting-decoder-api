package br.com.meetingdecoder.domain.shared;

import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.shared.exception.DomainValidationException;
import br.com.meetingdecoder.domain.shared.exception.EmptyFieldException;
import br.com.meetingdecoder.domain.shared.exception.InvalidDateException;
import br.com.meetingdecoder.domain.shared.exception.InvalidPriceException;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;
import br.com.meetingdecoder.domain.shared.validation.DomainValidation;
import br.com.meetingdecoder.domain.shared.validation.ErrorCollector;
import br.com.meetingdecoder.domain.shared.valueobject.NivelRelevancia;
import br.com.meetingdecoder.domain.shared.valueobject.ScoreConfiabilidade;
import br.com.meetingdecoder.infrastructure.web.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SharedKernelTest {

    @Test
    void shouldValidateCommonDomainValues() {
        assertThrows(EmptyFieldException.class, () -> DomainValidation.notBlank(" ", "name"));
        assertThrows(EmptyFieldException.class, () -> DomainValidation.notNull(null, "value"));
        assertThrows(InvalidPriceException.class, () -> DomainValidation.positive(BigDecimal.ZERO, "price"));
        assertThrows(
                InvalidDateException.class,
                () -> DomainValidation.notFuture(LocalDateTime.now().plusDays(1), "date")
        );

        assertDoesNotThrow(() -> DomainValidation.notBlank("valid", "name"));
        assertDoesNotThrow(() -> DomainValidation.positive(BigDecimal.ONE, "price"));
    }

    @Test
    void shouldCollectGroupAndMergeErrors() {
        ErrorCollector first = ErrorCollector.builder()
                .add(DomainError.of(DomainErrorCode.EMPTY_FIELD, "name"))
                .build();
        ErrorCollector merged = ErrorCollector.builder()
                .merge(first)
                .check(false, DomainError.of(DomainErrorCode.INVALID_FIELD, "status"))
                .build();

        assertEquals(2, merged.getErrors().size());
        assertEquals(1, merged.byField().get("name").size());
        assertEquals(1, merged.byCode().get("INVALID_FIELD").size());
        assertThrows(DomainValidationException.class, merged::validate);
    }

    @Test
    void shouldExposeDomainValidationExceptionDetails() {
        DomainValidationException exception = new DomainValidationException(List.of(
                DomainError.of(DomainErrorCode.EMPTY_FIELD, "name"),
                DomainError.of(DomainErrorCode.INVALID_EMAIL, "email")
        ));

        assertTrue(exception.hasField("email"));
        assertTrue(exception.hasErrorCode("EMPTY_FIELD"));
        assertTrue(exception.getMessage().contains("[name]"));
        assertThrows(IllegalArgumentException.class, () -> new DomainValidationException(List.of()));
    }

    @Test
    void shouldRepresentSuccessAndFailureResults() {
        Result<String> success = Result.success("data");
        Result<String> failure = Result.failure(DomainError.of(DomainErrorCode.NOT_FOUND, "id"));
        Result<Void> emptySuccess = Result.success();

        assertTrue(success.isSuccess());
        assertEquals("data", success.getData());
        assertTrue(success.getErrors().isEmpty());
        assertTrue(failure.isFailure());
        assertEquals("NOT_FOUND", failure.getErrors().getFirst().code());
        assertTrue(emptySuccess.isSuccess());
    }

    @Test
    void shouldClassifySharedConfidenceBoundaries() {
        assertEquals(NivelRelevancia.BAIXA, ScoreConfiabilidade.of(0.4).getNivel());
        assertEquals(NivelRelevancia.MEDIA, ScoreConfiabilidade.medio().getNivel());
        assertEquals(NivelRelevancia.ALTA, ScoreConfiabilidade.of(0.7).getNivel());
        assertThrows(IllegalArgumentException.class, () -> ScoreConfiabilidade.of(-0.1));
    }

    @Test
    void shouldMapIllegalArgumentToBadRequest() {
        var response = new GlobalExceptionHandler()
                .handleIllegalArgument(new IllegalArgumentException("invalid input"));

        assertEquals(400, response.getStatusCode().value());
        assertEquals("invalid input", response.getBody().get("message"));
    }
}
