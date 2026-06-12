package br.com.meetingdecoder.application.service.product;

import br.com.meetingdecoder.application.dto.produto.ProdutoOutput;
import br.com.meetingdecoder.application.ports.product.IFindProdutosByCategoriaUseCase;
import br.com.meetingdecoder.application.shared.result.Result;
import br.com.meetingdecoder.domain.insight.repository.IProdutoRepository;

import java.util.List;

public class FindProdutosByCategoriaUseCase
        implements IFindProdutosByCategoriaUseCase {

    private final IProdutoRepository repository;

    public FindProdutosByCategoriaUseCase(
            IProdutoRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Result<List<ProdutoOutput>> execute(
            String categoria
    ) {
        return Result.success(
                repository.findByCategoria(categoria)
                        .stream()
                        .map(ProdutoOutput::from)
                        .toList()
        );
    }
}
