package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.enums.ServiceCategory;
import br.com.meetingdecoder.domain.exception.EmptyFieldException;
import br.com.meetingdecoder.domain.exception.InvalidPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ServiceRecommendationTest {

    @Test
    @DisplayName("Should create a ServiceRecommendation successfully")
    void shouldCreateServiceRecommendation() {
        ServiceRecommendation service =
                ServiceRecommendation.create(
                        "Prometheus",
                        ServiceCategory.ERP,
                        "Descrição do serviço Prometheus",
                        new BigDecimal("299.90")
                );

        assertNotNull(service.getId());
        assertEquals("Prometheus", service.getName());
        assertEquals(ServiceCategory.ERP, service.getCategory());
        assertEquals("Descrição do serviço Prometheus", service.getDescription());
        assertEquals(new BigDecimal("299.90"), service.getPrice());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should throw an EmptyFieldException when Name is Invalid")
    void shouldThrowEmptyFieldExceptionWhenNameIsInvalid(String name) {
        assertThrows(
                EmptyFieldException.class,
                () -> ServiceRecommendation.create(
                        name,
                        ServiceCategory.ERP,
                        "Descrição do serviço Prometheus",
                        new BigDecimal("299.90")
                )
        );
    }

    @Test
    @DisplayName("Should throw an EmptyFieldException when Category is Invalid")
    void shouldThrowEmptyFieldExceptionWhenCategoryIsInvalid() {
        assertThrows(
                EmptyFieldException.class,
                () -> ServiceRecommendation.create(
                        "Prometheus",
                        null,
                        "Descrição do serviço Prometheus",
                        new BigDecimal("299.90")
                )
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should throw an EmptyFieldException when Description is Invalid")
    void shouldThrowEmptyFieldExceptionWhenDescriptionIsInvalid(String description) {
        assertThrows(
                EmptyFieldException.class,
                () -> ServiceRecommendation.create(
                        "Prometheus",
                        ServiceCategory.ERP,
                        description,
                        new BigDecimal("299.90")
                )
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1"})
    @DisplayName("Should throw an InvalidPriceException when Price is Invalid")
    void shouldThrowInvalidPriceExceptionWhenPriceIsInvalid(String price) {
        assertThrows(
                InvalidPriceException.class,
                () -> ServiceRecommendation.create(
                        "Prometheus",
                        ServiceCategory.ERP,
                        "Descrição do serviço Prometheus",
                        new BigDecimal(price)
                )
        );
    }
}