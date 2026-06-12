package br.com.meetingdecoder.domain.sale;

import br.com.meetingdecoder.domain.sale.enums.ClientSize;
import br.com.meetingdecoder.domain.sale.enums.ClientType;
import br.com.meetingdecoder.domain.sale.enums.NpsCategory;
import br.com.meetingdecoder.domain.sale.enums.SellerType;
import br.com.meetingdecoder.domain.sale.model.Client;
import br.com.meetingdecoder.domain.sale.model.Seller;
import br.com.meetingdecoder.domain.sale.valueobject.BillingRange;
import br.com.meetingdecoder.domain.sale.valueobject.Email;
import br.com.meetingdecoder.domain.sale.valueobject.NpsSnapshot;
import br.com.meetingdecoder.domain.shared.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static br.com.meetingdecoder.support.TestFixtures.client;
import static br.com.meetingdecoder.support.TestFixtures.seller;
import static org.junit.jupiter.api.Assertions.*;

class SaleDomainTest {

    @Test
    void shouldNormalizeValidEmailAndRejectInvalidEmail() {
        assertEquals("seller@example.com", Email.of("  SELLER@EXAMPLE.COM ").value());

        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> Email.of("invalid-email")
        );

        assertTrue(exception.hasField("email"));
        assertTrue(exception.hasErrorCode("INVALID_EMAIL"));
    }

    @Test
    void shouldValidateBillingRangeBoundaries() {
        BillingRange range = BillingRange.of(1, 10);
        assertEquals(1, range.minValue());
        assertEquals(10, range.maxValue());

        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> BillingRange.of(20, 10)
        );
        assertTrue(exception.hasField("billingRange"));
    }

    @Test
    void shouldClassifyNpsAtBoundaryValuesAndRejectFutureDate() {
        LocalDateTime past = LocalDateTime.now().minusDays(1);

        assertEquals(NpsCategory.LOW, NpsSnapshot.of(5.0, past).category());
        assertEquals(NpsCategory.MEDIUM, NpsSnapshot.of(5.01, past).category());
        assertEquals(NpsCategory.HIGH, NpsSnapshot.of(9.0, past).category());

        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> NpsSnapshot.of(11.0, LocalDateTime.now().plusDays(1))
        );
        assertEquals(2, exception.getErrors().size());
    }

    @Test
    void shouldDetermineClientSizeAndUpdateState() {
        Client client = client();
        assertEquals(ClientSize.SMALL, client.size());

        client.update(
                "New Corporate Name", null, null, null, null, null,
                null, null, null, ClientType.LEAD,
                BillingRange.of(1_000_000, 400_000_000), null
        );

        assertEquals("New Corporate Name", client.corporateReason());
        assertEquals(ClientType.LEAD, client.type());
        assertEquals(ClientSize.LARGE, client.size());
        assertNotNull(client.updatedAt());
    }

    @Test
    void shouldRejectBlankClientFieldDuringUpdate() {
        DomainValidationException exception = assertThrows(
                DomainValidationException.class,
                () -> client().update(
                        " ", null, null, null, null, null,
                        null, null, null, null, null, null
                )
        );

        assertTrue(exception.hasField("corporateReason"));
    }

    @Test
    void shouldCreateUpdateActivateAndDeactivateSeller() {
        Seller seller = seller();
        assertTrue(seller.active());
        assertEquals(SellerType.SELLER, seller.type());

        seller.deactivate();
        assertFalse(seller.active());

        seller.update(null, SellerType.MANAGER, "Ana Manager", Email.of("manager@example.com"));
        seller.activate();

        assertTrue(seller.active());
        assertEquals(SellerType.MANAGER, seller.type());
        assertEquals("Ana Manager", seller.name());
        assertEquals("manager@example.com", seller.email());
        assertNotNull(seller.updatedAt());
    }
}
