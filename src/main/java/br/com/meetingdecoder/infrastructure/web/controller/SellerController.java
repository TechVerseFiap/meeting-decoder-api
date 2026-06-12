package br.com.meetingdecoder.infrastructure.web.controller;

import br.com.meetingdecoder.application.command.seller.CreateSellerCommand;
import br.com.meetingdecoder.application.command.seller.UpdateSellerCommand;
import br.com.meetingdecoder.application.dto.seller.SellerOutput;
import br.com.meetingdecoder.application.ports.seller.*;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.sale.enums.SellerType;
import br.com.meetingdecoder.domain.sale.valueobject.Email;
import br.com.meetingdecoder.domain.sale.valueobject.SellerId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final ICreateSellerUseCase createSellerUseCase;
    private final IUpdateSellerUseCase updateSellerUseCase;
    private final IFindSellerByIdUseCase findSellerByIdUseCase;
    private final IActivateSellerUseCase activateSellerUseCase;
    private final IDeactivateSellerUseCase deactivateSellerUseCase;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateSellerRequest request) {
        CreateSellerCommand command = new CreateSellerCommand(
                UUID.randomUUID(),
                request.managerId(),
                request.type(),
                request.name(),
                request.email()
        );

        Result<SellerOutput> result = createSellerUseCase.execute(command);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableContent().body(result.getErrors());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Result<SellerOutput> result = findSellerByIdUseCase.execute(id);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UpdateSellerRequest request) {
        UpdateSellerCommand command = UpdateSellerCommand.builder()
                .managerId(request.managerId() != null ? SellerId.of(request.managerId()) : null)
                .type(request.type())
                .name(request.name())
                .email(request.email() != null ? Email.of(request.email()) : null)
                .build();

        Result<SellerOutput> result = updateSellerUseCase.execute(SellerId.of(id), command);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableContent().body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable UUID id) {
        Result<Void> result = activateSellerUseCase.execute(id);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable UUID id) {
        Result<Void> result = deactivateSellerUseCase.execute(id);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.noContent().build();
    }

    public record CreateSellerRequest(
            UUID managerId,
            SellerType type,
            String name,
            String email
    ) {}

    public record UpdateSellerRequest(
            UUID managerId,
            SellerType type,
            String name,
            String email
    ) {}
}
