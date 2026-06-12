package br.com.meetingdecoder.application.ports.product;

import br.com.meetingdecoder.application.dto.produto.ProdutoOutput;
import br.com.meetingdecoder.application.shared.result.Result;

import java.util.List;

public interface IFindProdutosByCategoriaUseCase {
    Result<List<ProdutoOutput>> execute(String categoria);
}
