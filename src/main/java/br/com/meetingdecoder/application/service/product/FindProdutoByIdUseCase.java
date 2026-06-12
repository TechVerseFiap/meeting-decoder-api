package br.com.meetingdecoder.application.service.product;

import br.com.meetingdecoder.application.dto.produto.ProdutoOutput;
import br.com.meetingdecoder.application.ports.product.IFindProdutoByIdUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.model.Produto;
import br.com.meetingdecoder.domain.insight.repository.IProdutoRepository;
import br.com.meetingdecoder.domain.insight.valueobject.ProdutoId;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

import java.util.Optional;
import java.util.UUID;

public class FindProdutoByIdUseCase
        implements IFindProdutoByIdUseCase {

    private final IProdutoRepository repository;

    public FindProdutoByIdUseCase(
            IProdutoRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<ProdutoOutput> execute(
            UUID produtoId
    ) {
        Optional<Produto> produto =
                repository.findById(
                        ProdutoId.of(produtoId)
                );

        return produto.map(value ->
                Result.success(
                        ProdutoOutput.from(value)
                )
        ).orElseGet(() ->
                Result.failure(
                        DomainError.of(
                                DomainErrorCode.NOT_FOUND,
                                "Produto",
                                produtoId.toString()
                        )
                )
        );
    }
}
