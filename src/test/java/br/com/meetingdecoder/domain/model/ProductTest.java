package br.com.meetingdecoder.domain.model;

import br.com.meetingdecoder.domain.enums.ServiceCategory;
import br.com.meetingdecoder.domain.insight.model.Product;
import br.com.meetingdecoder.domain.shared.exception.EmptyFieldException;
import br.com.meetingdecoder.domain.shared.exception.InvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private static final String DEFAULT_NAME = "Prometheus";
    private static final ServiceCategory DEFAULT_CATEGORY = ServiceCategory.ERP;
    private static final String DEFAULT_DESCRIPTION = "Descrição do serviço Prometheus";
    private static final BigDecimal DEFAULT_PRICE = new BigDecimal("299.90");

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.create(
                DEFAULT_NAME,
                DEFAULT_CATEGORY,
                DEFAULT_DESCRIPTION,
                DEFAULT_PRICE
        );
    }

    @Test
    void create_ValidParameters_ShouldCreateProduct() {
        Product service =
                Product.create(
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
                () -> Product.create(
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
                () -> Product.create(
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
                () -> Product.create(
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
                () -> Product.create(
                        DEFAULT_NAME,
                        DEFAULT_CATEGORY,
                        DEFAULT_DESCRIPTION,
                        new BigDecimal(price)
                )
        );
    }

    @Test
    void update_ValidParameters_ShouldUpdateAllFields() {
        product.update(
                "Datasul",
                ServiceCategory.FINANCE,
                "Descrição do serviço financeiro Datasul",
                new BigDecimal("199.99")
        );

        assertEquals("Datasul", product.getName());
        assertEquals(ServiceCategory.FINANCE, product.getCategory());
        assertEquals("Descrição do serviço financeiro Datasul", product.getDescription());
        assertEquals(new BigDecimal("199.99"), product.getPrice());
    }

    @Test
    void update_OnlyDescriptionAndPrice_ShouldUpdateSpecifiedFields() {
        product.update(
                null,
                null,
                "Descrição do serviço ERP Prometheus",
                new BigDecimal("399.99")
        );

        assertEquals(DEFAULT_NAME, product.getName());
        assertEquals(DEFAULT_CATEGORY, product.getCategory());
        assertEquals("Descrição do serviço ERP Prometheus", product.getDescription());
        assertEquals(new BigDecimal("399.99"), product.getPrice());
    }

    @Test
    void update_AllParametersNull_ShouldKeepOriginalValues() {
        product.update(
                null,
                null,
                null,
                null
        );

        assertEquals(DEFAULT_NAME, product.getName());
        assertEquals(DEFAULT_CATEGORY, product.getCategory());
        assertEquals(DEFAULT_DESCRIPTION, product.getDescription());
        assertEquals(DEFAULT_PRICE, product.getPrice());
    }

    @Test
    void update_InvalidName_ShouldThrowException() {
        assertThrows(
                EmptyFieldException.class,
                () -> product.update(
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
                () -> product.update(
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
                () -> product.update(
                        null,
                        null,
                        null,
                        new BigDecimal(price)
                )
        );
    }
}