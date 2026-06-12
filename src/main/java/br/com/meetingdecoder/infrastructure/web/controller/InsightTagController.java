package br.com.meetingdecoder.infrastructure.web.controller;

import br.com.meetingdecoder.application.dto.insighttag.InsightTagOutput;
import br.com.meetingdecoder.application.ports.insighttag.*;
import br.com.meetingdecoder.application.shared.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/insight-tags")
@RequiredArgsConstructor
public class InsightTagController {

    private final IFindInsightTagByIdUseCase findInsightTagByIdUseCase;
    private final IFindInsightTagByNameUseCase findInsightTagByNameUseCase;
    private final IListInsightTagsUseCase listInsightTagsUseCase;

    @GetMapping
    public ResponseEntity<?> listAll() {
        Result<List<InsightTagOutput>> result = listInsightTagsUseCase.execute();

        if (result.isFailure()) {
            return ResponseEntity.unprocessableEntity().body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Result<InsightTagOutput> result = findInsightTagByIdUseCase.execute(id);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/name/{nome}")
    public ResponseEntity<?> findByName(@PathVariable String nome) {
        Result<InsightTagOutput> result = findInsightTagByNameUseCase.execute(nome);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }
}
