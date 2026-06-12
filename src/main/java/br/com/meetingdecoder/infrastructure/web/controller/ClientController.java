package br.com.meetingdecoder.infrastructure.web.controller;

import br.com.meetingdecoder.application.command.client.CreateClientCommand;
import br.com.meetingdecoder.application.command.client.UpdateClientCommand;
import br.com.meetingdecoder.application.dto.client.ClientOutput;
import br.com.meetingdecoder.application.ports.client.*;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.enums.ClientSize;
import br.com.meetingdecoder.domain.sale.enums.ClientType;
import br.com.meetingdecoder.domain.sale.valueobject.BillingRange;
import br.com.meetingdecoder.domain.sale.valueobject.ClientId;
import br.com.meetingdecoder.domain.sale.valueobject.NpsSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ICreateClientUseCase createClientUseCase;
    private final IUpdateClientUseCase updateClientUseCase;
    private final IFindClientByIdUseCase findClientByIdUseCase;
    private final IDeleteClientUseCase deleteClientUseCase;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateClientRequest request) {
        NpsSnapshot nps = (request.npsNote() != null && request.npsDate() != null)
                ? NpsSnapshot.of(request.npsNote(), request.npsDate())
                : null;

        CreateClientCommand command = new CreateClientCommand(
                UUID.randomUUID(),
                request.externalId(),
                request.corporateReason(),
                request.fantasyName(),
                request.cnpj(),
                request.cnae(),
                request.segment(),
                request.city(),
                request.state(),
                request.country(),
                request.type(),
                BillingRange.of(request.billingMin(), request.billingMax()),
                nps
        );

        Result<ClientOutput> result = createClientUseCase.execute(command);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableContent().body(result.getErrors());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Result<ClientOutput> result = findClientByIdUseCase.execute(id);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UpdateClientRequest request) {
        NpsSnapshot nps = (request.npsNote() != null && request.npsDate() != null)
                ? NpsSnapshot.of(request.npsNote(), request.npsDate())
                : null;

        BillingRange billing = (request.billingMin() != null && request.billingMax() != null)
                ? BillingRange.of(request.billingMin(), request.billingMax())
                : null;

        UpdateClientCommand command = UpdateClientCommand.builder()
                .corporateReason(request.corporateReason())
                .fantasyName(request.fantasyName())
                .cnpj(request.cnpj())
                .cnae(request.cnae())
                .segment(request.segment())
                .size(request.size())
                .city(request.city())
                .state(request.state())
                .country(request.country())
                .type(request.type())
                .billingRange(billing)
                .npsSnapshot(nps)
                .build();

        Result<ClientOutput> result = updateClientUseCase.execute(ClientId.of(id), command);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableContent().body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Result<Void> result = deleteClientUseCase.execute(id);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.noContent().build();
    }

    public record CreateClientRequest(
            String externalId,
            String corporateReason,
            String fantasyName,
            String cnpj,
            String cnae,
            String segment,
            String city,
            String state,
            String country,
            ClientType type,
            double billingMin,
            double billingMax,
            Double npsNote,
            LocalDateTime npsDate
    ) {}

    public record UpdateClientRequest(
            String corporateReason,
            String fantasyName,
            String cnpj,
            String cnae,
            String segment,
            ClientSize size,
            String city,
            String state,
            String country,
            ClientType type,
            Double billingMin,
            Double billingMax,
            Double npsNote,
            LocalDateTime npsDate
    ) {}
}
