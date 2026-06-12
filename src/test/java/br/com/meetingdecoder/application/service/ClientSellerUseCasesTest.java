package br.com.meetingdecoder.application.service;

import br.com.meetingdecoder.application.command.client.CreateClientCommand;
import br.com.meetingdecoder.application.command.client.UpdateClientCommand;
import br.com.meetingdecoder.application.command.seller.CreateSellerCommand;
import br.com.meetingdecoder.application.command.seller.UpdateSellerCommand;
import br.com.meetingdecoder.application.service.client.*;
import br.com.meetingdecoder.application.service.seller.*;
import br.com.meetingdecoder.domain.sale.enums.ClientType;
import br.com.meetingdecoder.domain.sale.enums.SellerType;
import br.com.meetingdecoder.domain.sale.model.Client;
import br.com.meetingdecoder.domain.sale.model.Seller;
import br.com.meetingdecoder.domain.sale.repository.IClientRepository;
import br.com.meetingdecoder.domain.sale.repository.ISellerRepository;
import br.com.meetingdecoder.domain.sale.valueobject.BillingRange;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.Email;
import br.com.meetingdecoder.domain.sale.valueobject.NpsSnapshot;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static br.com.meetingdecoder.support.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientSellerUseCasesTest {
    private IClientRepository clientRepository;
    private ISellerRepository sellerRepository;

    @BeforeEach
    void setUp() {
        clientRepository = mock(IClientRepository.class);
        sellerRepository = mock(ISellerRepository.class);
    }

    @Test
    void shouldCreateClientAndPersistAggregate() {
        CreateClientCommand command = new CreateClientCommand(
                CLIENT_ID, "external", "Corporate", "Fantasy", "123", "456", "Tech",
                "Sao Paulo", "SP", "BR", ClientType.CUSTOMER,
                BillingRange.of(100, 500), NpsSnapshot.of(9.0, NOW)
        );

        var result = new CreateClientUseCase(clientRepository).execute(command);

        assertTrue(result.isSuccess());
        assertEquals(CLIENT_ID, result.getData().id());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void shouldFindClientOrReturnNotFound() {
        when(clientRepository.findById(any())).thenReturn(Optional.of(client()), Optional.empty());
        FindClientByIdUseCase useCase = new FindClientByIdUseCase(clientRepository);

        assertEquals(CLIENT_ID, useCase.execute(CLIENT_ID).getData().id());
        var missing = useCase.execute(CLIENT_ID);
        assertTrue(missing.isFailure());
        assertEquals("NOT_FOUND", missing.getErrors().getFirst().code());
    }

    @Test
    void shouldUpdateClientAndPersistChanges() {
        Client client = client();
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        UpdateClientCommand command = UpdateClientCommand.builder()
                .corporateReason("Updated Corporate")
                .billingRange(BillingRange.of(1_000, 10_000))
                .build();

        var result = new UpdateClientUseCase(clientRepository)
                .execute(ClientId.of(CLIENT_ID), command);

        assertEquals("Updated Corporate", result.getData().corporateReason());
        verify(clientRepository).save(client);
    }

    @Test
    void shouldNotUpdateMissingClient() {
        when(clientRepository.findById(any())).thenReturn(Optional.empty());

        var result = new UpdateClientUseCase(clientRepository)
                .execute(ClientId.of(CLIENT_ID), UpdateClientCommand.builder().build());

        assertTrue(result.isFailure());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void shouldDeleteExistingClientAndRejectMissingClient() {
        when(clientRepository.findById(any())).thenReturn(Optional.of(client()), Optional.empty());
        DeleteClientUseCase useCase = new DeleteClientUseCase(clientRepository);

        assertTrue(useCase.execute(CLIENT_ID).isSuccess());
        verify(clientRepository).deleteById(any());

        assertTrue(useCase.execute(CLIENT_ID).isFailure());
    }

    @Test
    void shouldCreateSellerAndPersistAggregate() {
        CreateSellerCommand command = new CreateSellerCommand(
                SELLER_ID, null, SellerType.SELLER, "Ana", "ANA@EXAMPLE.COM"
        );

        var result = new CreateSellerUseCase(sellerRepository).execute(command);

        assertTrue(result.isSuccess());
        assertEquals("ana@example.com", result.getData().email());
        verify(sellerRepository).save(any(Seller.class));
    }

    @Test
    void shouldFindSellerOrReturnNotFound() {
        when(sellerRepository.findById(any())).thenReturn(Optional.of(seller()), Optional.empty());
        FindSellerByIdUseCase useCase = new FindSellerByIdUseCase(sellerRepository);

        assertEquals(SELLER_ID, useCase.execute(SELLER_ID).getData().id());
        assertTrue(useCase.execute(SELLER_ID).isFailure());
    }

    @Test
    void shouldUpdateSellerAndPersistChanges() {
        Seller seller = seller();
        when(sellerRepository.findById(any())).thenReturn(Optional.of(seller));
        UpdateSellerCommand command = UpdateSellerCommand.builder()
                .type(SellerType.MANAGER)
                .name("Ana Manager")
                .email(Email.of("manager@example.com"))
                .build();

        var result = new UpdateSellerUseCase(sellerRepository)
                .execute(SellerId.of(SELLER_ID), command);

        assertEquals(SellerType.MANAGER, result.getData().type());
        assertEquals("Ana Manager", result.getData().name());
        verify(sellerRepository).save(seller);
    }

    @Test
    void shouldNotUpdateMissingSeller() {
        when(sellerRepository.findById(any())).thenReturn(Optional.empty());

        var result = new UpdateSellerUseCase(sellerRepository)
                .execute(SellerId.of(SELLER_ID), UpdateSellerCommand.builder().build());

        assertTrue(result.isFailure());
        verify(sellerRepository, never()).save(any());
    }

    @Test
    void shouldActivateAndDeactivateSeller() {
        Seller seller = seller();
        seller.deactivate();
        when(sellerRepository.findById(any())).thenReturn(Optional.of(seller));

        assertTrue(new ActivateSellerUseCase(sellerRepository).execute(SELLER_ID).isSuccess());
        assertTrue(seller.active());

        assertTrue(new DeactivateSellerUseCase(sellerRepository).execute(SELLER_ID).isSuccess());
        assertFalse(seller.active());
        verify(sellerRepository, times(2)).save(seller);
    }

    @Test
    void shouldReturnNotFoundWhenActivatingOrDeactivatingMissingSeller() {
        when(sellerRepository.findById(any())).thenReturn(Optional.empty());

        assertTrue(new ActivateSellerUseCase(sellerRepository).execute(SELLER_ID).isFailure());
        assertTrue(new DeactivateSellerUseCase(sellerRepository).execute(SELLER_ID).isFailure());
        verify(sellerRepository, never()).save(any());
    }
}
