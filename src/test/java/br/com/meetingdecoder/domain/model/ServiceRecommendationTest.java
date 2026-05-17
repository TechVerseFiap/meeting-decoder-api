package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.enums.ServiceCategory;
import br.com.meetingdecoder.domain.exception.EmptyFieldException;
import br.com.meetingdecoder.domain.exception.InvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ServiceRecommendationTest {

    private static final String DEFAULT_NAME = "Prometheus";
    private static final ServiceCategory DEFAULT_CATEGORY = ServiceCategory.ERP;
    private static final String DEFAULT_DESCRIPTION = "Descrição do serviço Prometheus";
    private static final BigDecimal DEFAULT_PRICE = new BigDecimal("299.90");

    private ServiceRecommendation serviceRecommendation;

    @BeforeEach
    void setUp() {
        serviceRecommendation = ServiceRecommendation.create(
                DEFAULT_NAME,
                DEFAULT_CATEGORY,
                DEFAULT_DESCRIPTION,
                DEFAULT_PRICE
        );
    }

    @Test
    void create_ValidParameters_ShouldCreateServiceRecommendation() {
        ServiceRecommendation service =
                ServiceRecommendation.create(
                        DEFAULT_NAME,
                        DEFAULT_CATEGORY,
                        DEFAULT_DESCRIPTION,
                        DEFAULT_PRICE
                );

        assertNotNull(service.getId());
        assertEquals(DEFAULT_NAME, service.getName());
        assertEquals(DEFAULT_CATEGORY, service.getCategory());
        assertEquals(DEFAULT_DESCRIPTION, service.getDescription());
        assertEquals(DEFAULT_PRICE, service.getPrice());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void create_InvalidName_ShouldThrowException(String name) {
        assertThrows(
                EmptyFieldException.class,
                () -> ServiceRecommendation.create(
                        name,
                        DEFAULT_CATEGORY,
                        DEFAULT_DESCRIPTION,
                        DEFAULT_PRICE
                )
        );
    }

    @Test
    void create_NullCategory_ShouldThrowException() {
        assertThrows(
                EmptyFieldException.class,
                () -> ServiceRecommendation.create(
                        DEFAULT_NAME,
                        null,
                        DEFAULT_DESCRIPTION,
                        DEFAULT_PRICE
                )
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void create_InvalidDescription_ShouldThrowException(String description) {
        assertThrows(
                EmptyFieldException.class,
                () -> ServiceRecommendation.create(
                        DEFAULT_NAME,
                        DEFAULT_CATEGORY,
                        description,
                        DEFAULT_PRICE
                )
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1"})
    void create_InvalidPrice_ShouldThrowException(String price) {
        assertThrows(
                InvalidPriceException.class,
                () -> ServiceRecommendation.create(
                        DEFAULT_NAME,
                        DEFAULT_CATEGORY,
                        DEFAULT_DESCRIPTION,
                        new BigDecimal(price)
                )
        );
    }

    @Test
    void update_ValidParameters_ShouldUpdateAllFields() {
        serviceRecommendation.update(
                "Datasul",
                ServiceCategory.FINANCE,
                "Descrição do serviço financeiro Datasul",
                new BigDecimal("199.99")
        );

        assertEquals("Datasul", serviceRecommendation.getName());
        assertEquals(ServiceCategory.FINANCE, serviceRecommendation.getCategory());
        assertEquals("Descrição do serviço financeiro Datasul", serviceRecommendation.getDescription());
        assertEquals(new BigDecimal("199.99"), serviceRecommendation.getPrice());
    }

    @Test
    void update_OnlyDescriptionAndPrice_ShouldUpdateSpecifiedFields() {
        serviceRecommendation.update(
                null,
                null,
                "Descrição do serviço ERP Prometheus",
                new BigDecimal("399.99")
        );

        assertEquals(DEFAULT_NAME, serviceRecommendation.getName());
        assertEquals(DEFAULT_CATEGORY, serviceRecommendation.getCategory());
        assertEquals("Descrição do serviço ERP Prometheus", serviceRecommendation.getDescription());
        assertEquals(new BigDecimal("399.99"), serviceRecommendation.getPrice());
    }

    @Test
    void update_AllParametersNull_ShouldKeepOriginalValues() {
        serviceRecommendation.update(
                null,
                null,
                null,
                null
        );

        assertEquals(DEFAULT_NAME, serviceRecommendation.getName());
        assertEquals(DEFAULT_CATEGORY, serviceRecommendation.getCategory());
        assertEquals(DEFAULT_DESCRIPTION, serviceRecommendation.getDescription());
        assertEquals(DEFAULT_PRICE, serviceRecommendation.getPrice());
    }

    @Test
    void update_InvalidName_ShouldThrowException() {
        assertThrows(
                EmptyFieldException.class,
                () -> serviceRecommendation.update(
                       "",
                       null,
                       null,
                       null
                )
        );
    }

    @Test
    void update_InvalidDescription_ShouldThrowException() {
        assertThrows(
                EmptyFieldException.class,
                () -> serviceRecommendation.update(
                        null,
                        null,
                        "",
                        null
                )
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1"})
    void update_InvalidPrice_ShouldThrowException(String price) {
        assertThrows(
                InvalidPriceException.class,
                () -> serviceRecommendation.update(
                        null,
                        null,
                        null,
                        new BigDecimal(price)
                )
        );
    }
}