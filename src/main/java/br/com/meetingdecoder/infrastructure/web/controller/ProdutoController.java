package br.com.meetingdecoder.infrastructure.web.controller;

import br.com.meetingdecoder.application.dto.produto.ProdutoOutput;
import br.com.meetingdecoder.application.ports.product.*;
import br.com.meetingdecoder.application.shared.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final IFindProdutoByIdUseCase findProdutoByIdUseCase;
    private final IFindProdutoByNameUseCase findProdutoByNameUseCase;
    private final IFindProdutosByCategoriaUseCase findProdutosByCategoriaUseCase;
    private final IFindProdutosByLinhaUseCase findProdutosByLinhaUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Result<ProdutoOutput> result = findProdutoByIdUseCase.execute(id);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/name/{nome}")
    public ResponseEntity<?> findByName(@PathVariable String nome) {
        Result<ProdutoOutput> result = findProdutoByNameUseCase.execute(nome);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> findByCategoria(@PathVariable String categoria) {
        Result<List<ProdutoOutput>> result = findProdutosByCategoriaUseCase.execute(categoria);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableEntity().body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/linha/{linha}")
    public ResponseEntity<?> findByLinha(@PathVariable String linha) {
        Result<List<ProdutoOutput>> result = findProdutosByLinhaUseCase.execute(linha);

        if (result.isFailure()) {
            return ResponseEntity.unprocessableEntity().body(result.getErrors());
        }

        return ResponseEntity.ok(result.getData());
    }
}
