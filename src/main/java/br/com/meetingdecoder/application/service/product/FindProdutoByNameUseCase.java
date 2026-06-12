package br.com.meetingdecoder.application.service.product;

import br.com.meetingdecoder.application.dto.produto.ProdutoOutput;
import br.com.meetingdecoder.application.ports.product.IFindProdutoByNameUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.model.Produto;
import br.com.meetingdecoder.domain.insight.repository.IProdutoRepository;
import br.com.meetingdecoder.domain.shared.validation.DomainError;
import br.com.meetingdecoder.domain.shared.validation.DomainErrorCode;

import java.util.Optional;

public class FindProdutoByNameUseCase
        implements IFindProdutoByNameUseCase {

    private final IProdutoRepository repository;

    public FindProdutoByNameUseCase(
            IProdutoRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<ProdutoOutput> execute(
            String nome
    ) {
        Optional<Produto> produto =
                repository.findByNome(nome);

        return produto.map(value ->
                Result.success(
                        ProdutoOutput.from(value)
                )
        ).orElseGet(() ->
                Result.failure(
                        DomainError.of(
                                DomainErrorCode.NOT_FOUND,
                                "Produto",
                                nome
                        )
                )
        );
    }
}
