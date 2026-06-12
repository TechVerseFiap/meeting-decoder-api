package br.com.meetingdecoder.infrastructure.web.controller;

import br.com.meetingdecoder.application.dto.insight.InsightOutput;
import br.com.meetingdecoder.application.ports.insight.*;
import br.com.meetingdecoder.application.shared.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/insights")
@RequiredArgsConstructor
public class InsightController {

    private final IFindInsightByIdUseCase findInsightByIdUseCase;
    private final IFindInsightsByTranscriptionUseCase findInsightsByTranscriptionUseCase;
    private final IFindActiveChurnRisksUseCase findActiveChurnRisksUseCase;
    private final IFindPriorityInsightsForSellerUseCase findPriorityInsightsForSellerUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Result<InsightOutput> result = findInsightByIdUseCase.execute(id);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/transcription/{transcriptionId}")
    public ResponseEntity<?> findByTranscription(@PathVariable UUID transcriptionId) {
        Result<List<InsightOutput>> result = findInsightsByTranscriptionUseCase.execute(transcriptionId);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableContent().body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/churn-risks/client/{clientId}")
    public ResponseEntity<?> findActiveChurnRisks(@PathVariable UUID clientId) {
        Result<List<InsightOutput>> result = findActiveChurnRisksUseCase.execute(clientId);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableContent().body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/priority/seller/{sellerId}")
    public ResponseEntity<?> findPriorityForSeller(@PathVariable UUID sellerId) {
        Result<List<InsightOutput>> result = findPriorityInsightsForSellerUseCase.execute(sellerId);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableContent().body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }
}
