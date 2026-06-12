package br.com.meetingdecoder.application.service.product;

import br.com.meetingdecoder.application.dto.produto.ProdutoOutput;
import br.com.meetingdecoder.application.ports.product.IFindProdutosByLinhaUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.repository.IProdutoRepository;

import java.util.List;

public class FindProdutosByLinhaUseCase
        implements IFindProdutosByLinhaUseCase {

    private final IProdutoRepository repository;

    public FindProdutosByLinhaUseCase(
            IProdutoRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<List<ProdutoOutput>> execute(
            String linha
    ) {
        return Result.success(
                repository.findByLinha(linha)
                        .stream()
                        .map(ProdutoOutput::from)
                        .toList()
        );
    }
}
